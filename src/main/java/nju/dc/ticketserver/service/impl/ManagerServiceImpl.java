package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.po.ManagerPO;
import nju.dc.ticketserver.po.ModifyApplicationPO;
import nju.dc.ticketserver.po.RegApplicationPO;
import nju.dc.ticketserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Override
    public int confirmRegVenue(String venueID) {
        return managerDao.confirmRegVenue(venueID);
    }

    @Override
    public int confirmModifyVenueInfo(String venueID) {
        return managerDao.confirmModifyVenueInfo(venueID);
    }

    @Override
    public ManagerPO getManagerPO(String email) {
        return managerDao.getManagerPO(email);
    }

    @Override
    public List<RegApplicationPO> getVenueRegApplicationPOs() {
        return managerDao.getVenueRegApplicationPOs();
    }

    @Override
    public List<ModifyApplicationPO> getVenueModifyApplicationPOs() {
        return managerDao.getVenueModifyApplicationPOs();
    }
}
