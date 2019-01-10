import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
                CardInfo cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.PYMRECORD.toString());
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));

                MessageCarousel msgcrl = new MessageCarousel();
                msgcrl.setId(ctx.getCtxAttr("_bundle").get("id"));
                msgcrl.setType(Message.Type.CAROUSEL);
                List<Info> infos = cardinfo.getInfos();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getCardtype());
                    try {
                        column.setImageUrl(new URI(detail.getImageUrl()));
                        column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "xxx\$1"));
                        column.setTitle(detail.getTitle() + (info.getCcl().equals("N") ? CitiUtil.sharingQuota:"" ));
                        Content content = new Content();
                        content.setText(detail.getTitle() + (info.getCcl().equals("N") ? CitiUtil.sharingQuota:"" ));
                        content.setType(Content.Type.GRID);
                        content.addHeader(new Header("繳款日",null));
                        content.addHeader(new Header("完成日",null));
                        content.addHeader(new Header("金額","right"));
                        int newLineCnt = 0;
                        for (Pym pym:info.getPyms()) {
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
                            field.setText(CitiUtil.formatMoney(pym.getTxnamount(), CitiUtil.fontColor.BLUE));
                            field.setIsBold(true);
                            field.setAlign("right");
                            row.addField(field);
                            content.addRow(row);
                        }
                        column.addContent(content);

                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    msgcrl.addColumn(column);
                }

                jsonInString = mapper.writeValueAsString(msgcrl);
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
