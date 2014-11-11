package com.sunlights.trade.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.sunlights.account.vo.TotalCapitalInfo;
import com.sunlights.common.AppConst;
import com.sunlights.common.MsgCode;
import com.sunlights.common.utils.MessageUtil;
import com.sunlights.common.vo.Message;
import com.sunlights.common.vo.PageVo;
import com.sunlights.trade.service.TradeService;
import com.sunlights.trade.service.impl.TradeServiceImpl;
import com.sunlights.trade.vo.CapitalProductTradeVo;
import com.sunlights.trade.vo.TradeFormVo;
import com.sunlights.trade.vo.TradeSearchFormVo;
import com.sunlights.trade.vo.TradeVo;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * <p>Project: fsp</p>
 * <p>Title: WebTradeSrvice.java</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2014 Sunlights.cc</p>
 * <p>All Rights Reserved.</p>
 *
 * @author <a href="mailto:jiaming.wang@sunlights.cc">wangJiaMing</a>
 */
@Transactional
public class TradeController extends Controller{
    private Form<TradeSearchFormVo> tradeSearchFormVoForm = Form.form(TradeSearchFormVo.class);
    private Form<TradeFormVo> tradeFormVoForm = Form.form(TradeFormVo.class);

    private TradeService tradeService = new TradeServiceImpl();

    public Result getTradeList(){
        Logger.info("----------getTradeList start ------------");
        Http.Cookie cookie = Controller.request().cookie(AppConst.TOKEN);
        String token = cookie == null ? null : cookie.value();

        TradeSearchFormVo tradeSearchFormVo = tradeSearchFormVoForm.bindFromRequest().get();
        PageVo pageVo = new PageVo();
        pageVo.setIndex(tradeSearchFormVo.getIndex());
        pageVo.setPageSize(tradeSearchFormVo.getPageSize());

        List<TradeVo> list = tradeService.getTradeListByToken(token, tradeSearchFormVo, pageVo);
        pageVo.setList(list);
        pageVo.getFilter().clear();

        Message message = new Message(MsgCode.OPERATE_SUCCESS);
        JsonNode json = MessageUtil.getInstance().msgToJson(message, pageVo);
        Logger.info("----------getTradeList end" + json);
        return ok(json);
    }

    public Result findCapitalProductDetailTrade(){
        Logger.info("----------findCapitalProductDetailTrade start ------------");
        Http.Cookie cookie = Controller.request().cookie(AppConst.TOKEN);
        String token = cookie == null ? null : cookie.value();

        TradeSearchFormVo tradeSearchFormVo = tradeSearchFormVoForm.bindFromRequest().get();

        CapitalProductTradeVo capitalProductTradeVo = tradeService.findCapitalProductDetailTrade(token, tradeSearchFormVo);

        Message message = new Message(MsgCode.OPERATE_SUCCESS);
        JsonNode json = MessageUtil.getInstance().msgToJson(message, capitalProductTradeVo);
        Logger.info("----------findCapitalProductDetailTrade end--------------\n" + json);
        return ok(json);
    }

    public Result tradeOrder(){
        Logger.info("----------tradeOrder start ------------");
        Http.Cookie cookie = Controller.request().cookie(AppConst.TOKEN);
        String token = cookie == null ? null : cookie.value();

        TradeFormVo tradeFormVo = tradeFormVoForm.bindFromRequest().get();

        TotalCapitalInfo totalCapitalInfo = tradeService.tradeFundOrder(tradeFormVo, token);

        JsonNode json = MessageUtil.getInstance().msgToJson(new Message(MsgCode.TRADE_SUCCESS), totalCapitalInfo);
        Logger.info("----------tradeOrder end: ------------\n" + json);

        return ok(json);
    }


    public Result tradeRedeem(){
        Logger.info("----------tradeRedeem start ------------");
        Http.Cookie cookie = Controller.request().cookie(AppConst.TOKEN);
        String token = cookie == null ? null : cookie.value();

        TradeFormVo tradeFormVo = tradeFormVoForm.bindFromRequest().get();

        TotalCapitalInfo totalCapitalInfo = tradeService.tradeFundRedeem(tradeFormVo, token);

        JsonNode json = MessageUtil.getInstance().msgToJson(new Message(MsgCode.TRADE_SUCCESS), totalCapitalInfo);
        Logger.info("----------tradeRedeem end: ------------\n" + json);

        return ok(json);
    }

}
