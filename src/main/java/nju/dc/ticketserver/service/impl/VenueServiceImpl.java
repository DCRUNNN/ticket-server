package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.dao.utils.DaoUtils;
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
    private DaoUtils daoUtils;

    @Override
    public String applyRegVenue(RegApplicationPO regApplicationPO) {
        String venueID = daoUtils.createVenueID();
        regApplicationPO.setVenueID(venueID);
        regApplicationPO.setApplicationTime(daoUtils.setSignUpDate());
        regApplicationPO.setState("待审核");

        //处理area
        String seat = convertSeatInfo(regApplicationPO.getArea(), regApplicationPO.getRow(), regApplicationPO.getSeat());
        regApplicationPO.setSeat(seat);
        int check = venueDao.applyRegVenue(regApplicationPO);

        return check==1? venueID:"fail" ;
    }

    private String convertSeatInfo(String area,int row,String seat) {

//        输入： A-2/B-2/C-3/D-5 ,12 , 50/50/60/60/10/10/10/5/5/5/5/5
//        输出：A-01-01,50;A-02-01,50/B-03-01,60;B-04-01,60/C-05-01,10;C-06-01,10;C-07-01,10/D-08-01,10;D-09-01,5;D-10-01,5;D-11-01,5;D-12-01,5
        String[] areas = area.split("/");
        String[] seats = seat.split("/");
        StringBuffer result = new StringBuffer();

        int k = 0;

        for(int i=0;i<areas.length;i++) {
            String areaName = areas[i].split("-")[0];
            int areaRow = Integer.parseInt(areas[i].split("-")[1]);
            for(int j=0;j<areaRow;j++) {
                result.append(areaName).append("-").append(formatInteger(++k, 2)).append("-01").append(",").append(seats[2 * i + j]);
                if (j != areaRow - 1) {
                    result.append(";");
                } else {
                    result.append("/");
                }
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    private String formatInteger(int i, int length) {
        return String.format("%0" + length + "d", i);
    }

    @Override
    public int applyModifyVenueInfo(ModifyApplicationPO modifyApplicationPO) {
        modifyApplicationPO.setApplicationTime(daoUtils.setSignUpDate());
        modifyApplicationPO.setState("待审核");

        return venueDao.applyModifyVenueInfo(modifyApplicationPO);
    }

    @Override
    public int releaseShowPlan(ShowPO showPO, List<ShowSeatPO> showSeatPOList) {
        return venueDao.releaseShowPlan(showPO, showSeatPOList);
    }

}
