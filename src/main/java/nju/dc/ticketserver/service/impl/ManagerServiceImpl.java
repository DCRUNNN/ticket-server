package nju.dc.ticketserver.service.impl;

import nju.dc.ticketserver.dao.ManagerDao;
import nju.dc.ticketserver.dao.ShowDao;
import nju.dc.ticketserver.dao.UserDao;
import nju.dc.ticketserver.dao.VenueDao;
import nju.dc.ticketserver.po.*;
import nju.dc.ticketserver.service.ManagerService;
import nju.dc.ticketserver.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private ShowService showService;

    @Autowired
    private VenueDao venueDao;

    @Autowired
    private UserDao userDao;

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

    @Override
    public int giveMoneyToVenue(String showID, double paymentRatio) {
        //判断演出是否结束

        boolean canPay = showService.isShowEnd(showID);

        if (canPay) {
            ShowPO showPO = showService.getShowPOByID(showID);
            return managerDao.giveMoneyToVenue(showPO, paymentRatio);
        }else{
            return -1;
        }
    }

    @Override
    public List<ShowPO> getNeedToPayShows() {
        return showDao.getNeedToPayShows();
    }

    @Override
    public List<ModifyApplicationPO> getVenueModifyApps(String venueID) {
        return managerDao.getVenueModifyApps(venueID);
    }

    @Override
    public String[][] getVenuesIncomeInfo() {

        List<VenuePO> venuePOList = venueDao.getVenuePOList();

        String[][] result = new String[2][];

        String[] venueNames = new String[venuePOList.size()];
        String[] incomes = new String[venuePOList.size()];

        for(int i=0;i<venuePOList.size();i++) {
            venueNames[i] = venuePOList.get(i).getVenueName();
            incomes[i] = venuePOList.get(i).getIncome() + "";
        }

        result[0] = venueNames;
        result[1] = incomes;

        return result;
    }

    @Override
    public String[][] getVIPTypeInfo() {

        List<UserPO> userPOList = userDao.getUserPOList();
        int isVIP = 0;
        int notVIP = 0;

        for (UserPO userPO : userPOList) {
            if (userPO.isVIP()) {
                isVIP++;
            }else{
                notVIP++;
            }
        }

        String[][] result = new String[2][];

        String[] type = {"会员", "非会员"};
        String[] nums = new String[2];
        nums[0] = isVIP + "";
        nums[1] = notVIP + "";

        result[0] = type;
        result[1] = nums;
        return result;
    }

    @Override
    public int[] getVIPLevelInfo() {

        List<UserPO> userPOList = userDao.getUserPOList();

        int vip0 = 0;
        int vip1 = 0;
        int vip2 = 0;
        int vip3 = 0;
        int vip4 = 0;
        int vip5 = 0;
        int vip6 = 0;

        for (UserPO userPO : userPOList) {
            switch(userPO.getVipLevel()){
                case 0:
                    vip0++;
                    break;
                case 1:
                    vip1++;
                    break;
                case 2:
                    vip2++;
                    break;
                case 3:
                    vip3++;
                    break;
                case 4:
                    vip4++;
                    break;
                case 5:
                    vip5++;
                    break;
                case 6:
                    vip6++;
                    break;
            }
        }

        int[] result = new int[7];
        result[0] = vip0;
        result[1] = vip1;
        result[2] = vip2;
        result[3] = vip3;
        result[4] = vip4;
        result[5] = vip5;
        result[6] = vip6;

        return result;
    }

    @Override
    public List<TicketsFinancePO> getTicketFinancePOs() {
        return managerDao.getTicketFinancePOs();
    }
}
