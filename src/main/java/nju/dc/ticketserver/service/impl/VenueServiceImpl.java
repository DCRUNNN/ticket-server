package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.dao.utils.VenueDaoUtils;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueDao venueDao;

    @Autowired
    private VenueDaoUtils venueDaoUtils;

    @Override
    public int applyRegVenue(RegApplicationPO regApplicationPO) {
        String venueID = venueDaoUtils.createVenueID();
        regApplicationPO.setVenueID(venueID);
        regApplicationPO.setApplicationTime(venueDaoUtils.setCreateDate());
        regApplicationPO.setState("待审核");

        //处理area

        return venueDao.applyRegVenue(regApplicationPO);
    }

    @Override
    public int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO) {
        modifyApplicationPO.setApplicationTime(venueDaoUtils.setCreateDate());
        modifyApplicationPO.setState("待审核");

        return venueDao.applyModifyVenueInfo(modifyApplicationPO);
    }

    @Override
    public int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList) {
        return venueDao.releaseShowPlan(showPO, showSeatPOList);
    }

}
