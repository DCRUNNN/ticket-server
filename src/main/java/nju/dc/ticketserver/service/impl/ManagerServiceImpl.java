package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
