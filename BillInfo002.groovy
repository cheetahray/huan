import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.TreeMap;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intumit.citi.backend.CardInfo;
import com.intumit.citi.backend.Info;
import com.intumit.citi.frontend.Action;
import com.intumit.citi.frontend.Column;
import com.intumit.citi.frontend.Content;
import com.intumit.citi.frontend.Menu;
import com.intumit.citi.frontend.Message;
import com.intumit.citi.frontend.MessageButtons;
import com.intumit.citi.frontend.MessageCarousel;
import com.intumit.citi.frontend.MessageText;
import com.intumit.citi.CitiUtil;
import com.intumit.citi.Result;
import com.intumit.citi.CitiDeep;
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.JSONArray;
import org.apache.commons.lang.StringUtils;
import com.intumit.systemconfig.WiseSystemConfigFacade;
import com.intumit.solr.robot.RobotFormalAnswers;
private Content newContent(Content.Type type, String text) {
    Content content = new Content();
    content.setType(type);
    content.setText(text);
    return content;
}

private Action newAction(Action.Type type, String text, String url) {
    Action action = new Action();
    action.setText(text);
    action.setType(Action.Type.URL);
    action.setUrl(url);
	return action;
}

private String formalAns(String key)
{
    return RobotFormalAnswers.getAnswers(ctx.getTenant().getId(),key).get(0).toString();  
}

private boolean checkBlkcd(String s, HashSet set)
{
    String[] arr = s.split("");
    for(String ch: arr)
    {
        if(set.contains(ch))
            return true;
    }
    return false;
}

private boolean checkABBlockCode(String blkcd, HashSet set, String currbal)
{
    boolean isAB = checkBlkcd(blkcd, set);
    boolean isBlkcdEmpty = StringUtils.isEmpty(currbal);
    return ( !isAB || 
               ( isAB && 
                   ( isBlkcdEmpty || 
                       (   !isBlkcdEmpty && currbal.matches(CitiUtil.isNumeric) && 
                           Integer.parseInt(currbal) != 0 
                       ) 
                   ) 
               )                   
           );
}

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        JSONObject jsonobj = (JSONObject)ctx.getCtxAttr("_bundle");
        System.out.print("http://twwsb-chatbot1u.apac.nsroot.net:9080/wise/qa-ajax.jsp?apikey=" + jsonobj.get("apikey")
                  + "&id=" + URLEncoder.encode(jsonobj.get("id"), "UTF-8") + "&q=" + jsonobj.get("q") );
        if(jsonobj.has("id")) {
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.STATEMENT.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.STATEMENT.toString());
                    //ctx.setCtxAttr(Result.Postfix.STATEMENT.toString(),cardinfo);
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    long rightnow = System.currentTimeMillis();
                    long diffInMillies = Math.abs(rightnow - sdf.parse(ctx.getLastResponseAttribute("originalQuestionTime",rightnow)).getTime());
                    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);  
                    if (diff > Integer.parseInt(CitiUtil.getProperties("cacheSecs","200")))
                    {
                       cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.STATEMENT.toString());
                       ctx.setCtxAttr(Result.Postfix.STATEMENT.toString(),cardinfo);
                    }
                }
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));
                HashSet set1 = new HashSet<>(Arrays.asList(CitiUtil.s1));
                set1.addAll(CitiDeep.logos(15));
                HashSet set2 = new HashSet<>(Arrays.asList(CitiUtil.s1));
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null && checkABBlockCode( info.getBlkcd(), set2, info.getCurrBal() ))
                    {
                      column.setImageUrl(detail.getImageUrl());
                      column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "···· \$1"));
                      column.setTitle(detail.getTitle() + ( ( set1.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(),set1) )?formalAns("alreadyCancel"):"") );
                      column.addContent( newContent( Content.Type.TEXT, formalAns("totalAmountofCurrentBill") +
                                                     CitiUtil.formatMoney( info.getEndBal(), CitiUtil.fontColor.BLUE ) ) );
                      column.addContent( newContent( Content.Type.TEXT, formalAns("miniAmountPayment") + CitiUtil.formatMoney(info.getTotAmtDue(), CitiUtil.fontColor.BLUE) ) );
                      column.addContent( newContent( Content.Type.TEXT, formalAns("billCheckoutDate") + info.getStmtday() ) );
                      int endbal = Integer.valueOf(info.getEndBal());
                      if (endbal > 0)
                      {
                          column.addContent( newContent( Content.Type.TEXT, formalAns("paymentDeadline") +
                                                     info.getDueDt() + (info.getAutopay().equals("Y")?formalAns("autoTransfer"):"") ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, formalAns("paymentDeadline") + formalAns("noPayment") ) );
                      }
                      //column.addExternalActions(newAction(Action.Type.URL,"本期帳單明細", CitiUtil.getMyLink() + WiseSystemConfigFacade.getInstance().get().getContextPath()
                      // + "/citi-detail.jsp?cardno=" + (cnt++) + "&apikey=" + ctx.getCtxAttr("_bundle").get("apikey") + "&id=" + ctx.getCtxAttr("_bundle").get("id")));
                      column.addExternalActions(newAction(Action.Type.URL,"未出帳交易明細",CitiUtil.unTranDetail));
                      if(StringUtils.isEmpty(info.getBlkcd()))
                      {
                          column.addExternalActions(newAction(Action.Type.URL,"申請帳單分期",CitiUtil.getMyLink() + "/qa-ajax.jsp?apikey=" + jsonobj.getString("apikey")
		                                                                                + "&id=" + jsonobj.getString("id") + "&q=我要申請帳單分期"));
                      }
                      //column.addExternalActions(newAction(Action.Type.URL,"立刻繳款",CitiUtil.payRightNow));
                      String tmId = String.valueOf(detail.getPriority());
                      if(tm.containsKey(tmId))
                      {
                        tm.put(String.format("%3s", tmId) + String.valueOf(++tmInc), column);
                      }
                      else
                      {
                        tm.put(String.format("%3s", tmId) + String.valueOf(tmInc), column);
                      }
                    }
                }
                Iterator i = tm.entrySet().iterator();
                MessageCarousel msgcrl = null;
                if(i.hasNext()) {
                    msgcrl = new MessageCarousel();
                    msgcrl.setId("");
                    msgcrl.setType(Message.Type.CAROUSEL);
                }
                tmInc = 0;
                while(i.hasNext()) {
                    Map.Entry me = (Map.Entry)i.next();
                    msgcrl.addColumn(me.getValue());
                    tmInc++;
                }
                MessageText msgtxt = null;
                if(0 == tmInc)
                {
                    msgtxt = new MessageText();
                    msgtxt.setId("");
                    msgtxt.setType(Message.Type.TEXT);
                    msgtxt.setText(formalAns("noStateMent"));
                    jsonInString = mapper.writeValueAsString(msgtxt);
                }
                else
                {
                    jsonInString = mapper.writeValueAsString(msgcrl);
                }
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
