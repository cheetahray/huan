import java.util.List;
import java.util.HashSet;
import java.util.Arrays;
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
private Content newContent(Content.Type type, String text) {
    Content content = new Content();
    content.setType(type);
    content.setText(text);
    return content;
}

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        if(ctx.getRequestAttribute(CitiUtil.userid) != null) {
                String UserID = ctx.getRequestAttribute(CitiUtil.userid);
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.CARDINFO.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.CARDINFO.toString());
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
                       cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.CARDINFO.toString());
                       ctx.setCtxAttr(Result.Postfix.CARDINFO.toString(),cardinfo);
                    }
                }
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));
                HashSet set1 = new HashSet<>(Arrays.asList(CitiUtil.s1));
                List bigcome = CitiDeep.logos(29,29);
                set1.addAll(bigcome);
                //HashSet set2 = new HashSet<>(Arrays.asList(CitiUtil.s2));
                //set2.addAll(CitiUtil.s1);
                //set2.addAll(bigcome);
                HashSet set3 = new HashSet<>(Arrays.asList(CitiUtil.s3));
                set3.addAll(CitiUtil.s1);
                set3.addAll(CitiUtil.s2);
                set3.remove("U");
                set3.addAll(CitiDeep.logos(20,20));
                set3.addAll(CitiDeep.logos(26,27));
                MessageCarousel msgcrl = new MessageCarousel();
                msgcrl.setId(ctx.getCtxAttr("_bundle").get("id"));
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
                      if(set1.contains(info.getLogo()) || set1.contains(info.getBlkcd()))
                      {
                          column.setTitle(detail.getTitle() + CitiUtil.alreadyCancel);
                      }
                      else
                      {
                          column.setTitle(detail.getTitle() + ( StringUtils.equals(info.getCcl(), "Y") ? CitiUtil.singleQuota:CitiUtil.sharingQuota ));
                      }
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.billCheckoutDate + info.getStmtday() ) );
                      if(set3.contains(info.getLogo()) || set3.contains(info.getBlkcd()))
                      {
                          column.addContent( newContent( Content.Type.TEXT, ( detail.getReward() + "<br />\${transfer}" ) ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, ( detail.getReward() + CitiUtil.newLine + info.getAvlPoint() ) ) );
                      }
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.totalCredit + CitiUtil.formatMoney(info.getCurrBal(), CitiUtil.fontColor.BLUE) ) );
                      int crl = Integer.valueOf(info.getCrL());
                      if (crl < 0)
                      {
                          crl *= -1;
                          column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(crl, CitiUtil.currentOverpayAmout) ) );
                      }
                      else
                      {
                          column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(crl, CitiUtil.usedQuata) ) );
                      }
                      int availcl = Integer.valueOf(info.getAvailCl());
                      if (availcl < 0)
                      {
                          availcl = 0;
                      }
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.formatMoney(availcl, CitiUtil.availableCredit) ) );
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
