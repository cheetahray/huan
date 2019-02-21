import java.util.List;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.TreeMap;
import java.text.DateFormat;
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
        System.out.println("http://twwsb-chatbot1u.apac.nsroot.net:9080/wise/qa-ajax.jsp?apikey=" + jsonobj.get("apikey")
                  + "&id=" + URLEncoder.encode(jsonobj.get("id"), "UTF-8") + "&q=" + jsonobj.get("q") );
        //System.out.println(jsonobj);
        if(jsonobj.has("id")) {
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.RESENDESTMT.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.RESENDESTMT.toString());
                    //ctx.setCtxAttr(Result.Postfix.RESENDESTMT.toString(),cardinfo);
                }
                else if(false)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    long rightnow = System.currentTimeMillis();
                    long diffInMillies = Math.abs(rightnow - sdf.parse(ctx.getLastResponseAttribute("originalQuestionTime",rightnow)).getTime());
                    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);  
                    if (diff > Integer.parseInt(CitiUtil.getProperties("cacheSecs","200")))
                    {
                       cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.RESENDESTMT.toString());
                       ctx.setCtxAttr(Result.Postfix.RESENDESTMT.toString(),cardinfo);
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
                boolean estmt = false;
                boolean hasEmail = true;
                HashSet set2 = new HashSet<>(Arrays.asList(CitiUtil.s1));
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    if(StringUtils.isNotEmpty(info.getEmail()))
                    {
                        Column column = new Column();
                        CitiDeep detail = CitiDeep.alist(info.getLogo());
                        if(detail != null && checkABBlockCode( info.getBlkcd(), set2, info.getCurrBal() ))
                        {
                            try {
                                column.setImageUrl(detail.getImageUrl());
                                column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "···· \$1"));
                                column.setTitle(detail.getTitle() + ( ( set1.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(),set1) )?formalAns("alreadyCancel"):""));
                                
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                long rightnow = System.currentTimeMillis();
                                if(StringUtils.isNotBlank(info.getStmtday()))
                                {
                                    Date date = sdf.parse(info.getStmtday());
                                    long diffInMillies = (rightnow - date.getTime());
                                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);  
                                    column.addContent( newContent(Content.Type.TEXT, formalAns("checkoutBill") + CitiUtil.formatDate(date,"MM/dd") ) );
                                    if(diff <= 2 && diff >= 0)
                                    {
                                        column.addContent( newContent(Content.Type.TEXT, formalAns("waitForTwoDays")) );
                                    }
                                    else
                                    {
                                        column.addContent( newContent(Content.Type.TEXT, formalAns("emailBox") + info.getEmail() ) );
                                    }
                                }
                                else
                                {
                                    column.addContent( newContent(Content.Type.TEXT, formalAns("checkoutBill") + "－－－") );
                                    column.addContent( newContent(Content.Type.TEXT, formalAns("clickCustomerService")) );
                                }
                            } catch (URISyntaxException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            int tmId = detail.getPriority();
                            String threeS = String.format("%3d", tmId);
                            if(tm.containsKey(threeS + String.valueOf(tmInc)))
                            {
                              tm.put(threeS + String.valueOf(++tmInc), column);
                            }
                            else
                            {
                              tm.put(threeS + String.valueOf(tmInc), column);
                            }
                        }
                        if(false == estmt)
                        {
                            estmt = StringUtils.equals("N",info.getEstmt());
                        }
                    }
                    else
                    {
                        hasEmail = false;
                        break;
                    }
                }
                MessageCarousel msgcrl = null;
                MessageButtons msgbtn = null;
                MessageText msgtxt = null;
                loveEarth = "https://www.citibank.com.tw/global_docs/chi/cb/160913_estmtcola/index.html?icid=TWCCACAZHHOMELFBA";
                Iterator i = tm.entrySet().iterator();
                if(false == hasEmail)
                {
                    msgtxt = new MessageText();
                    msgtxt.setId("");
                    msgtxt.setType(Message.Type.TEXT);
                    msgtxt.setText(formalAns("clickCustomerService"));
                    jsonInString = mapper.writeValueAsString(msgtxt);
                    jsonInString = "[" + jsonInString + "]";
                }
                else if(!i.hasNext())
                {
                    msgbtn = new MessageButtons();
                    msgbtn.setId("");
                    msgbtn.setType(Message.Type.BUTTONS);  
                    msgbtn.setText(formalAns("nowYouHaveNoEmail"));
                    msgbtn.addAction(newAction(Action.Type.URL,formalAns("eLoveEarthTogether"), loveEarth));
                    //msgbtn.addAction(newAction(Action.Type.URL,"帳單資訊", CitiUtil.getMyLink()
                    //             + "/qa-ajax.jsp?apikey=" + jsonobj.getString("apikey") // + "&UserID=" + jsonobj.getString("UserID")
                    //             + "&id=" + jsonobj.getString("id") + "&q=帳單應交金額及繳款日" // + CitiUtil.billInfo
                    //             ));
                    jsonInString = mapper.writeValueAsString(msgbtn);
                    jsonInString = "[" + jsonInString + "]";
                }
                else
                {
                    msgcrl = new MessageCarousel();
                    msgcrl.setId("");
                    msgcrl.setType(Message.Type.CAROUSEL);  
                    while(i.hasNext()) {
                        Map.Entry me = (Map.Entry)i.next();
                        msgcrl.addColumn(me.getValue());
                    }
                    jsonInString = mapper.writeValueAsString(msgcrl);
                    if(estmt)
                    {
                        msgtxt = new MessageText();
                        msgtxt.setId("");
                        msgtxt.setType(Message.Type.TEXT);
                        msgtxt.setText("<div class='talk_btns'><div class='talk_box blue citi_icon bearW'><span class='blue'>" + formalAns("poleBear") + 
                               "</span><br><br><b class='notice_btn'>注意事項</b> <div class='notice_content'> " + formalAns("sevenDays") + 
                               "</div></div><div class='btn_item center'><ul><li class='btnRedir' data-id='" + loveEarth + "'><a href='javascript:;'>"
                               + formalAns("IAgreeEmail") + "</a></li></ul></div></div>");
                        String jsonInTxt = mapper.writeValueAsString(msgtxt);
                        jsonInString = "[" + jsonInString + "," + jsonInTxt + "]";
                    }
                    else
                    {
                        jsonInString = "[" + jsonInString + "]";
                    }
                }
                
                ctx.response.put("Messages", new JSONArray(jsonInString));                
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
