package com.autumnframework.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.autumnframework.common.dao.bomapper.LoginInfoMapper;
import com.autumnframework.common.model.po.LoginInfo;
import com.autumnframework.common.model.vo.AreaView;
import com.autumnframework.common.model.vo.ChartsView;
import com.autumnframework.common.service.interfaces.ILogManage;
import com.autumnframework.common.architect.constant.ResponseCode;
import com.autumnframework.common.architect.utils.ResponseMsgUtil;
import com.autumnframework.common.model.bo.DataPageResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Junlan Shuai[shuaijunlan@gmail.com].
 * @date Created on 19:43 2017/10/31.
 */
@Service
public class LogManageImpl implements ILogManage {
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Override
    public DataPageResponseMsg selectAllLoginInfo(int page, int limit) {
        return ResponseMsgUtil.returnCodeMessage(ResponseCode.REQUEST_SUCCESS, loginInfoMapper.selectAllLoginInfo((page-1)*limit, limit), loginInfoMapper.getAllLoginInfoConut());
    }

    @Override
    public DataPageResponseMsg selectLoginInfoByUserName(String username, int page, int limit) {
        return ResponseMsgUtil.returnCodeMessage(ResponseCode.REQUEST_SUCCESS, loginInfoMapper.selectLoginInfoByUserName(username, (page-1)*limit, limit), loginInfoMapper.getUserLoginInfoConut(username));
    }

    @Override
    public int insertLoginInfo(LoginInfo loginInfo) {
        return loginInfoMapper.insert(loginInfo);
    }

    @Override
    public JSONObject getLoginInfoCharts() {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        for (ChartsView chartsView : loginInfoMapper.getLoginInfoCharts()){
            stringList.add(chartsView.getX());
            integerList.add(chartsView.getY());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("X", stringList);
        jsonObject.put("Y", integerList);
        return jsonObject;
    }

    @Override
    public List<AreaView> getLoginInfoArea() {

        List<AreaView> areaViewList = loginInfoMapper.getLoginInfoArea();
        for (AreaView areaView : areaViewList){
            String name = areaView.getName();
            if (name.lastIndexOf("省") != -1){
                areaView.setName(areaView.getName().substring(0, name.length()-1));
            }else if (name.lastIndexOf("市") != -1){
                areaView.setName(areaView.getName().substring(0, name.length()-1));
            }else if (name.equals("内蒙古自治区")){
                areaView.setName(areaView.getName().substring(0, name.length()-3));
            }else if (name.lastIndexOf("自治区") != -1){
                areaView.setName(areaView.getName().substring(0, 2));
            }else if (name.lastIndexOf("行政区") != -1){
                areaView.setName(areaView.getName().substring(0, 2));
            }
        }
        return areaViewList;
    }
}
