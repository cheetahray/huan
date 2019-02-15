import java.util.List;
import java.util.HashSet;
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
import com.intumit.citi.CitiDeep
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.JSONArray;
import org.apache.commons.lang.StringUtils;
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

private Column setColumn(Column column, CitiDeep detail, boolean isGray) {
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
        /*
        System.out.print(detail.getApplyNow());
        if(StringUtils.isBlank(detail.getApplyNow()) || detail.getApplyNow().startsWith("不提"))
        {
            column.addInternalActions(new Action(Action.Type.URL,detail.getApplyNow(),"","disabled"));
        }
        else if(isGray)
        {
            column.addInternalActions(new Action(Action.Type.URL,"馬上申請",detail.getApplyNow(),"disabled"));
        }
        else
        {
            column.addInternalActions(new Action(Action.Type.URL,"馬上申請",detail.getApplyNow(),null));
        }*/
        return column;
    } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}

private String formalAns(String key)
{
    return RobotFormalAnswers.getAnswers(ctx.getTenant().getId(),key).get(0).toString();  
}

try {
    if (ctx.currentQuestion != null && ctx.currentQuestion.length() > 0) {
        JSONObject jsonobj = (JSONObject)ctx.getCtxAttr("_bundle");
        System.out.print("http://twwsb-chatbot1u.apac.nsroot.net:9080/wise/qa-ajax.jsp?apikey=" + jsonobj.get("apikey")
                  + "&id=" + URLEncoder.encode(jsonobj.get("id"), "UTF-8") + "&q=" + jsonobj.get("q") );
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
                set2.addAll(bigcome);
                List places = Arrays.asList(Arrays.asList(false,new HashSet<>(CitiDeep.logos(1)),"807"), 
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(2)),"558"), 
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(3)),"554"),
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(4)),"451"),
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(5)),"420"),
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(6)),"947"),
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(7)),"463"),
                                            Arrays.asList(false,new HashSet<>(CitiDeep.logos(8)),"241")
                                           );
                MessageCarousel msgcrl = new MessageCarousel();
                msgcrl.setId("");
                msgcrl.setType(Message.Type.CAROUSEL);
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null  && !set2.contains(info.getLogo()) && !checkBlkcd(info.getBlkcd(),set2))
                    {
                      for (List place: places)
                      {
                          HashSet set = place.get(1);
                          if(place.get(0) == false && set.contains(info.getLogo()))
                          {
                              place.set(0,true);
                          }
                      }
                      if(set1.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(),set1))
                      {
                          column.setTitle(detail.getTitle() + formalAns("alreadyCancel") );
                      }
                      else
                      {
                          column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "···· \$1"));
                          column.setTitle(detail.getTitle());
                      }
                      String tmId = String.valueOf(detail.getPriority());
                      if(tm.containsKey(tmId))
                      {
                        tm.put(String.format("%3s", tmId) + String.valueOf(++tmInc), setColumn(column, detail, true));
                      }
                      else
                      {
                        tm.put(String.format("%3s", tmId) + String.valueOf(tmInc), setColumn(column, detail, true));
                      }
                    }
                }
                System.out.println(tm);
                Iterator i = tm.entrySet().iterator();
                while(i.hasNext()) {
                    Map.Entry me = (Map.Entry)i.next();
                    msgcrl.addColumn(me.getValue());
                }
                for (List place: places)
                {
                    if(place.get(0) == false)
                    {
                        Column column = new Column();
                        CitiDeep detail = CitiDeep.alist(place.get(2));
                        column.setImageText("");
                        column.setTitle(detail.getTitle().replace("白金", "").replace("悠遊", ""));
                        msgcrl.addColumn(setColumn(column, detail, false));   
                    }
                }
                jsonInString = mapper.writeValueAsString(msgcrl);
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
