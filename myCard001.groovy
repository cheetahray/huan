import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
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
import com.intumit.solr.robot.RobotFormalAnswers;
import java.net.URLEncoder;
private Content newContent(Content.Type type, String text) {
    Content content = new Content();
    content.setType(type);
    content.setText(text);
    return content;
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

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        JSONObject jsonobj = (JSONObject)ctx.getCtxAttr("_bundle");
        if(jsonobj.has("id")) {
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.CARDINFO.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.CARDINFO.toString());
                    //ctx.setCtxAttr(Result.Postfix.CARDINFO.toString(),cardinfo);
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    long rightnow = System.currentTimeMillis();
                    long diffInMillies = Math.abs(rightnow - sdf.parse(ctx.getLastResponseAttribute("originalQuestionTime",rightnow)).getTime());
                    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);  
                    if (diff > Integer.parseInt(CitiUtil.getProperties("cacheSecs","200")))
                    {
                       cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.CARDINFO.toString());
                       ctx.setCtxAttr(Result.Postfix.CARDINFO.toString(),cardinfo);
                    }
                }
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));
                List bigcome = CitiDeep.logos(15);
                HashSet set1 = new HashSet<>(Arrays.asList(CitiUtil.s1));
                set1.addAll(bigcome);
                HashSet set2 = new HashSet<>(Arrays.asList(CitiUtil.s2));
                //set2.addAll(CitiUtil.s1);
                set2.addAll(bigcome);
                HashSet set3 = new HashSet<>(Arrays.asList(CitiUtil.s3));
                set3.addAll(CitiUtil.s1);
                set3.addAll(CitiUtil.s2);
                set3.remove("U");
                set3.addAll(CitiDeep.logos(8));
                set3.addAll(CitiDeep.logos(13));
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null && !set2.contains(info.getLogo()) && !checkBlkcd(info.getBlkcd(),set2))
                    {
                      column.setImageUrl(detail.getImageUrl());
                      column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "···· \$1"));
                      if(set1.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(),set1))
                      {
                          column.setTitle(detail.getTitle() + formalAns("alreadyCancel") );
                      }
                      else
                      {
                          column.setTitle(detail.getTitle() + ( StringUtils.equals(info.getCcl(), "Y") ? formalAns("sharingQuota"):formalAns("singleQuota") ) );
                      }
                      column.addContent( newContent( Content.Type.TEXT, formalAns("billCheckoutDate") + info.getStmtday() ) );
                      if(set3.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(),set3))
                      {
                          column.addContent( newContent( Content.Type.TEXT, ( detail.getReward() + "<br />\${transfer}" ) ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, ( detail.getReward() + CitiUtil.newLine + info.getAvlPoint() ) ) );
                      }
                      column.addContent( newContent( Content.Type.TEXT, formalAns("totalCredit") + CitiUtil.formatMoney(info.getCrL(), CitiUtil.fontColor.BLUE) ) );
                      int currbal = Integer.valueOf(info.getCurrBal());
                      if (currbal < 0)
                      {
                          currbal *= -1;
                          column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(currbal, formalAns("currentOverpayAmout") ) ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(currbal, formalAns("usedQuata") ) ) );
                      }
                      int availcl = Integer.valueOf(info.getAvailCl());
                      if (availcl < 0)
                      {
                          availcl = 0;
                      }
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(availcl, formalAns("availableCredit") ) ) );
                      String tmId = String.valueOf(detail.getId());
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
                    msgtxt.setText(formalAns("noCardInfo"));
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
