import java.util.List;
import java.util.TreeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intumit.citi.backend.CardInfo;
import com.intumit.citi.backend.Info;
import com.intumit.citi.backend.Pym;
import com.intumit.citi.backend.Txn;
import com.intumit.citi.frontend.Action;
import com.intumit.citi.frontend.Box;
import com.intumit.citi.frontend.BoxGrid;
import com.intumit.citi.frontend.Column;
import com.intumit.citi.frontend.Content;
import com.intumit.citi.frontend.Field;
import com.intumit.citi.frontend.GridRow;
import com.intumit.citi.frontend.Header;
import com.intumit.citi.frontend.Menu;
import com.intumit.citi.frontend.Message;
import com.intumit.citi.frontend.MessageButtons;
import com.intumit.citi.frontend.MessageCarousel;
import com.intumit.citi.frontend.MessageText;
import com.intumit.citi.frontend.Row;
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
        System.out.print("http://twwsb-chatbot1u.apac.nsroot.net:9080/wise/qa-ajax.jsp?apikey=" + jsonobj.get("apikey")
                  + "&id=" + URLEncoder.encode(jsonobj.get("id"), "UTF-8") + "&q=" + jsonobj.get("q") );
        if(jsonobj.has("id")) {
                CardInfo cardinfo = ctx.getCtxAttr(Result.Postfix.PYMRECORD.toString());
                if (cardinfo == null || cardinfo.getResult().getCode() != 0) 
                {
                    cardinfo = CitiUtil.getSmartMenu(jsonobj.get("id"), Result.Postfix.PYMRECORD.toString());
                    ctx.setCtxAttr(Result.Postfix.PYMRECORD.toString(),cardinfo);
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
                HashSet set3 = new HashSet<>(Arrays.asList(CitiUtil.s2));
                set3.remove("W");
                int tmInc = 0;
                List<Info> infos = cardinfo.getInfos();
                TreeMap tm = new TreeMap();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null)
                    {
                      boolean blkcdLess0 = false;
                      try {
                          column.setImageUrl(detail.getImageUrl());
                          column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "···· \$1"));
                          boolean alreadyCancel = ( set1.contains(info.getLogo()) || checkBlkcd(info.getBlkcd(), set1) );
                          column.setTitle(detail.getTitle() + (alreadyCancel?formalAns("alreadyCancel"):""));
                          Content content = new Content();
                          content.setText(detail.getTitle() + (alreadyCancel?formalAns("alreadyCancel"):""));
                          content.setType(Content.Type.GRID);
                          content.addHeader(new Header("繳款日",null));
                          content.addHeader(new Header("入帳日",null));
                          content.addHeader(new Header("金額","right"));
                          int newLineCnt = 0;
                          for (Pym pym:info.getPyms()) {
                              int txncode = ( StringUtils.isNumeric(pym.getTxncode())?Integer.parseInt(pym.getTxncode()):0 );
                              if(txncode > 700 && txncode < 800)
                              {
                                  Row row = new Row();
                                  Field field = new Field();
                                  field.setText(pym.getTxndate());
                                  row.addField(field);
                                  field = new Field();
                                  field.setText(pym.getDuedate());
                                  row.addField(field);
                                  field = new Field();
                                  field.setText("");
                                  row.addField(field);
                                  if (newLineCnt > 0)
                                      row.setIsAlternatingRow(true);
                                  newLineCnt++;
                                  content.addRow(row);
                                  row = new Row();
                                  field = new Field();
                                  field.setText(pym.getTxndesc());
                                  row.addField(field);
                                  field = new Field();
                                  field.setText("");
                                  row.addField(field);
                                  field = new Field();
                                  field.setText(pym.getTxnamount());
                                  field.setIsBold(true);
                                  field.setAlign("right");
                                  row.addField(field);
                                  content.addRow(row);
                              }
                          }
                          if(newLineCnt != 0)
                          {
                              column.addContent(content);
                          }
                          else if(checkBlkcd(info.getBlkcd(),set3))
                              blkcdLess0 = true;
                          else if(StringUtils.isEmpty(info.getBlkcd()))
                              column.addContent( newContent(Content.Type.TEXT, formalAns("noNewPymRecord")) );
                          else if(checkBlkcd(info.getBlkcd(),set2) && StringUtils.isNotEmpty(info.getCurrBal()) &&
                              info.getCurrBal().matches(CitiUtil.isNumeric))                   
                          {
                              if(Integer.parseInt(info.getCurrBal()) != 0)
                                  column.addContent( newContent(Content.Type.TEXT, formalAns("noNewPymRecord")) );
                              else
                                  blkcdLess0 = true;
                          }
                          else 
                              column.addContent( newContent(Content.Type.TEXT, formalAns("noNewPymRecord")) );
                      } catch (URISyntaxException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                      }
                      if(false == blkcdLess0)
                      {
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
                }
                Iterator i = tm.entrySet().iterator();
                MessageCarousel msgcrl = null;
                MessageText msgtxt = null;
                if(i.hasNext())
                {
                    msgcrl = new MessageCarousel();
                    msgcrl.setId("");
                    msgcrl.setType(Message.Type.CAROUSEL);
                    while(i.hasNext()) {
                        Map.Entry me = (Map.Entry)i.next();
                        msgcrl.addColumn(me.getValue());
                    }
                    jsonInString = mapper.writeValueAsString(msgcrl);
                }
                else
                {
                    msgtxt = new MessageText();
                    msgtxt.setId("");
                    msgtxt.setType(Message.Type.TEXT);
                    msgtxt.setText(formalAns("noNewPymRecord"));
                    jsonInString = mapper.writeValueAsString(msgtxt);
                }
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
