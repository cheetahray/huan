import java.util.List;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
import com.intumit.citi.CitiDeep
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.JSONArray;

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

private Column setColumn(Column column, CitiDeep detail) {
    try {
        column.setImageUrl(detail.getImageUrl());
        Content content = new Content();
        content.setText(detail.getOfferName1() + CitiUtil.newLine + detail.getOfferText1());
        content.setType(Content.Type.TEXT);
        column.addContent(content);
        content = new Content();
        content.setText(detail.getOfferName2() + CitiUtil.newLine + detail.getOfferText2());
        content.setType(Content.Type.TEXT);
        column.addContent(content);
        content = new Content();
        content.setText(detail.getOfferName3() + CitiUtil.newLine + detail.getOfferText3());
        content.setType(Content.Type.TEXT);
        column.addContent(content);
        Action action = new Action();
        action.setType(Action.Type.URL);
        action.setText("了解更多");
        action.setUrl(detail.getKnowMore());
        column.addInternalActions(action);
        column.addInternalActions(new Action(Action.Type.URL,"馬上申請",detail.getApplyNow(),null));
        return column;
    } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        if(ctx.getRequestAttribute(CitiUtil.userid) != null) {
                String UserID = ctx.getRequestAttribute(CitiUtil.userid);
                CardInfo cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.CARDINFO.toString());
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));
                List places = Arrays.asList(Arrays.asList(false,new HashSet<>(Arrays.asList("807")),"807"), 
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("559","558")),"558"), 
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("593","595","554","597","510","550")),"554"),
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("451","596","847","848","849","461","028","432","010","120","439","496","532","539")),"596"),
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("420","421")),"421"),
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("947","462","802","263","197")),"263"),
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("948","463","494","492","004","005","018","023","130","138","230","238","333","334","335","338","417","427","517","527","592","594")),"492"),
                                            Arrays.asList(false,new HashSet<>(Arrays.asList("241","266")),"241")
                                           );
                MessageCarousel msgcrl = new MessageCarousel();
                msgcrl.setId(ctx.getCtxAttr("_bundle").get("id"));
                msgcrl.setType(Message.Type.CAROUSEL);
                List<Info> infos = cardinfo.getInfos();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null)
                    {
                      for (List place: places)
                      {
                          HashSet set = place.get(1);
                          if(place.get(0) == false && set.contains(info.getLogo()))
                          {
                              place.set(0,true);
                              System.out.println(info.getLogo());
                          }
                      }
                      column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "xxx\$1"));
                      column.setTitle(detail.getTitle() + (info.getCcl().equals("N") ? CitiUtil.sharingQuota:CitiUtil.singleQuota ));
                      msgcrl.addColumn(setColumn(column, detail));
                    }
                }
                for (List place: places)
                {
                    if(place.get(0) == false)
                    {
                        Column column = new Column();
                        CitiDeep detail = CitiDeep.alist(place.get(2));
                        column.setImageText("xxx0000");
                        column.setTitle(detail.getTitle().replace("白金", "").replace("悠遊", ""));
                        msgcrl.addColumn(setColumn(column, detail));   
                    }
                }
                jsonInString = mapper.writeValueAsString(msgcrl);

                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
