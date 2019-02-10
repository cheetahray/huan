import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.TreeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        if(ctx.getRequestAttribute(CitiUtil.userid) != null) {
                String UserID = ctx.getRequestAttribute(CitiUtil.userid);
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.STATEMENT.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.STATEMENT.toString());
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
                       cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.STATEMENT.toString());
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
                set1.addAll(CitiDeep.logos(29,29));
                MessageCarousel msgcrl = new MessageCarousel();
                JSONObject jsonobj = (JSONObject)ctx.getCtxAttr("_bundle");
                msgcrl.setId(jsonobj.get("id"));
                msgcrl.setType(Message.Type.CAROUSEL);
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null)
                    {
                      column.setImageUrl(detail.getImageUrl());
                      column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "xxx\$1"));
                      column.setTitle(detail.getTitle() + (set1.contains(info.getLogo())?CitiUtil.alreadyCancel:""));
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.totalAmountofCurrentBill +
                                                     CitiUtil.formatMoney( info.getEndBal(), CitiUtil.fontColor.BLUE ) ) );
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.miniAmountPayment + CitiUtil.formatMoney(info.getTotAmtDue(), CitiUtil.fontColor.BLUE) ) );
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.billCheckoutDate + info.getStmtday() ) );
                      int endbal = Integer.valueOf(info.getEndBal());
                      if (endbal > 0)
                      {
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.paymentDeadline +
                                                     info.getDueDt() + (info.getAutopay().equals("Y")?CitiUtil.autoTransfer:"") ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, CitiUtil.noPayment + info.getDueDt() ) );
                      }
                      //column.addExternalActions(newAction(Action.Type.URL,"本期帳單明細", CitiUtil.getMyLink() + WiseSystemConfigFacade.getInstance().get().getContextPath()
                      // + "/citi-detail.jsp?cardno=" + (cnt++) + "&apikey=" + ctx.getCtxAttr("_bundle").get("apikey") + "&id=" + ctx.getCtxAttr("_bundle").get("id")));
                      column.addExternalActions(newAction(Action.Type.URL,"未出帳交易明細",CitiUtil.unTranDetail));
                      column.addExternalActions(newAction(Action.Type.URL,"申請帳單分期",CitiUtil.getMyLink() + "/qa-ajax.jsp?apikey=" + jsonobj.getString("apikey")
		                                                                                + "&id=" + jsonobj.getString("id") + "&q=我要申請帳單分期"));
                      column.addExternalActions(newAction(Action.Type.URL,"立刻繳款",CitiUtil.payRightNow));
                      String tmId = String.valueOf(detail.getId());
                      if(tm.containsKey(tmId))
                      {
                        tm.put(tmId + "!" + (tmId++), column);
                      }
                      else
                      {
                        tm.put(tmId, column);
                      }
                    }
                }
                Iterator i = tm.entrySet().iterator();
                while(i.hasNext()) {
                    Map.Entry me = (Map.Entry)i.next();
                    msgcrl.addColumn(me.getValue());
                }
                jsonInString = mapper.writeValueAsString(msgcrl);
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
