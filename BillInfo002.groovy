import java.util.List;

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
import com.intumit.citi.CitiDeep;
import org.apache.wink.json4j.JSONObject;
import org.apache.wink.json4j.JSONArray;
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
                CardInfo cardinfo = CitiUtil.getSmartMenu(UserID, Result.Postfix.STATEMENT.toString());
                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result();
                result.setCode(cardinfo.getResult().getCode());
                result.setMessage(cardinfo.getResult().getMessage());
                String jsonInString = mapper.writeValueAsString(result);
                ctx.response.put("Result", new JSONObject(jsonInString));
                 
                MessageCarousel msgcrl = new MessageCarousel();
                msgcrl.setId(ctx.getCtxAttr("_bundle").get("id"));
                msgcrl.setType(Message.Type.CAROUSEL);
                int cnt=1;
                List<Info> infos = cardinfo.getInfos();
                for (Info info:infos) {
                    Column column = new Column();
                    CitiDeep detail = CitiDeep.alist(info.getLogo());
                    if(detail != null)
                    {
                      column.setImageUrl(detail.getImageUrl());
                      column.setImageText(info.getCardno().replaceFirst(".*(\\d{4})", "xxx\$1"));
                      column.setTitle(detail.getTitle() + (info.getCcl().equals("N") ? CitiUtil.sharingQuota:CitiUtil.singleQuota ));
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.totalAmountofCurrentBill +
                                                     CitiUtil.formatMoney( info.getEndBal(), CitiUtil.fontColor.BLUE ) ) );
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.miniAmountPayment + CitiUtil.formatMoney(info.getTotAmtDue(), CitiUtil.fontColor.BLUE) ) );
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.billCheckoutDate + info.getStmtday() ) );
                      column.addContent( newContent( Content.Type.TEXT, CitiUtil.paymentDeadline +
                                                     info.getDueDt() + (info.getAutopay().equals("Y")?CitiUtil.autoTransfer:"") ) );
                      //column.addExternalActions(newAction(Action.Type.URL,"本期帳單明細", CitiUtil.getMyLink() + WiseSystemConfigFacade.getInstance().get().getContextPath()
                      // + "/citi-detail.jsp?cardno=" + (cnt++) + "&apikey=" + ctx.getCtxAttr("_bundle").get("apikey") + "&id=" + ctx.getCtxAttr("_bundle").get("id")));
                      column.addExternalActions(newAction(Action.Type.URL,"未出帳交易明細",CitiUtil.unTranDetail));
                      column.addExternalActions(newAction(Action.Type.URL,"申請帳單分期",CitiUtil.applyBilling));
                      column.addExternalActions(newAction(Action.Type.URL,"立刻繳款",CitiUtil.payRightNow));
                      msgcrl.addColumn(column);
                    }
                }

                jsonInString = mapper.writeValueAsString(msgcrl);
                ctx.response.put("Messages", new JSONArray("[" + jsonInString + "]"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
}
