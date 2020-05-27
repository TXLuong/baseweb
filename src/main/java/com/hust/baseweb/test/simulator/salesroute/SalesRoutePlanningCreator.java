package com.hust.baseweb.test.simulator.salesroute;

import com.google.gson.Gson;
import com.hust.baseweb.applications.customer.entity.PartyDistributor;
import com.hust.baseweb.applications.customer.entity.PartyRetailOutlet;
import com.hust.baseweb.applications.sales.entity.RetailOutletSalesmanVendor;
import com.hust.baseweb.applications.sales.model.salesman.SalesmanOutputModel;
import com.hust.baseweb.applications.salesroutes.entity.SalesRouteConfig;
import com.hust.baseweb.applications.salesroutes.entity.SalesRoutePlanningPeriod;
import com.hust.baseweb.applications.salesroutes.entity.SalesRouteVisitFrequency;
import com.hust.baseweb.applications.salesroutes.model.salesrouteconfigcustomer.CreateSalesRouteConfigRetailOutletInputModel;
import com.hust.baseweb.test.simulator.Constants;
import com.hust.baseweb.test.simulator.HttpPostExecutor;
import com.hust.baseweb.test.simulator.Login;

import java.util.List;
import java.util.Random;

public class SalesRoutePlanningCreator {

    private Random rand = new Random();
    private Thread thread = null;
    private String token;
    private HttpPostExecutor executor = new HttpPostExecutor();
    private DistributorManager distributorManager = null;//new DistributorManager();
    private RetailOutletManager retailOutletManager = null;//new RetailOutletManager();
    private SalesmanManager salesmanManager = null;//new SalesmanManager();
    private SalesRoutePlanningPeriodManager salesRoutePlanningPeriodManager;
    private SalesRouteVisitFrequencyManager salesRouteVisitFrequencyManager;
    private SalesRouteConfigManager salesRouteConfigManager;
    private RetailOutletSalesmanDistributorManager retailOutletSalesmanDistributorManager;

    Random R = new Random();

    public SalesRoutePlanningPeriod selectSalesRoutePlanningPeriod(List<SalesRoutePlanningPeriod> salesRoutePlanningPeriods) {

        return salesRoutePlanningPeriods.get(R.nextInt(salesRoutePlanningPeriods.size()));
    }

    public SalesmanOutputModel selectPartySalesman(List<SalesmanOutputModel> partySalesmanList) {

        return partySalesmanList.get(R.nextInt(partySalesmanList.size()));
    }

    public PartyDistributor selectPartyDistributor(List<PartyDistributor> partyDistributors) {

        return partyDistributors.get(R.nextInt(partyDistributors.size()));
    }

    public PartyRetailOutlet selectPartyRetailOutlet(List<PartyRetailOutlet> partyRetailOutlets) {

        return partyRetailOutlets.get(R.nextInt(partyRetailOutlets.size()));
    }

    public SalesRouteVisitFrequency selectVisitFrequency(List<SalesRouteVisitFrequency> salesRouteVisitFrequencies) {

        return salesRouteVisitFrequencies.get(R.nextInt(salesRouteVisitFrequencies.size()));
    }

    public SalesRouteConfig selectSalesRouteConfig(List<SalesRouteConfig> salesRouteConfigs) {

        return salesRouteConfigs.get(R.nextInt(salesRouteConfigs.size()));
    }

    public void run() {

        token = Login.login("admin", "123");
        distributorManager = new DistributorManager(token);
        retailOutletManager = new RetailOutletManager(token);
        salesmanManager = new SalesmanManager(token);
        salesRoutePlanningPeriodManager = new SalesRoutePlanningPeriodManager(token);
        salesRouteVisitFrequencyManager = new SalesRouteVisitFrequencyManager(token);
        salesRouteConfigManager = new SalesRouteConfigManager(token);
        retailOutletSalesmanDistributorManager = new RetailOutletSalesmanDistributorManager(token);

        List<SalesRoutePlanningPeriod> salesRoutePlanningPeriodList = salesRoutePlanningPeriodManager.getListSalesRoutePlanningPeriods();
        List<SalesmanOutputModel> partySalesmanList = salesmanManager.getListSalesman();
        SalesRoutePlanningPeriod selectedSalesRoutePlanningPeriod = selectSalesRoutePlanningPeriod(
            salesRoutePlanningPeriodList);
        SalesmanOutputModel selectedPartySalesman = selectPartySalesman(partySalesmanList);

        List<PartyDistributor> partyDistributorList = null;
        for (SalesmanOutputModel sm : partySalesmanList) {
            partyDistributorList = distributorManager.getListDistributorsOfSalesman(sm.getPartyId());
            if (partyDistributorList != null && partyDistributorList.size() > 0) {
                selectedPartySalesman = sm;
//                System.out.println("selected salesman = " + selectedPartySalesman.getPartyId());
                break;
            }
        }

        //List<PartyDistributor> partyDistributorList =  distributorManager.getListDistributorsOfSalesman(selectedPartySalesman.getPartyId()); //distributorManager.getListDistributors();

        PartyDistributor selectedPartyDistributor = selectPartyDistributor(partyDistributorList);

        List<PartyRetailOutlet> partyRetailOutletList = retailOutletManager.getRetailoutletsOfSalesmanAndDistributor(
            selectedPartySalesman.getPartyId(),
            selectedPartyDistributor.getPartyId()); //retailOutletManager.getRetailoutlets();

        PartyRetailOutlet selectedPartyRetailOutlet = selectPartyRetailOutlet(partyRetailOutletList);

        List<SalesRouteVisitFrequency> salesRouteVisitFrequencies = salesRouteVisitFrequencyManager.getListSalesRouteVisitFrequency();
        SalesRouteVisitFrequency visitFrequency = selectVisitFrequency(salesRouteVisitFrequencies);

        List<SalesRouteConfig> salesRouteConfigs = salesRouteConfigManager.getListSalesRouteConfigs();
        SalesRouteConfig selectedSalesRouteConfig = selectSalesRouteConfig(salesRouteConfigs);


//        for(PartyDistributor d: partyDistributorList) {
//            System.out.println("Distributor " + d.getDistributorName());
//        }
//        for(SalesmanOutputModel sm: partySalesmanList){
//            System.out.println("Salesman " + sm.getFullName() + " id = " + sm.getUserLoginId());
//        }
//        for(PartyRetailOutlet ro: partyRetailOutletList){
//            System.out.println("RetailOutlet " + ro.getRetailOutletName());
//        }
        RetailOutletSalesmanVendor retailOutletSalesmanVendor = retailOutletSalesmanDistributorManager.getRetailOutletSalesmanDistributor(
            selectedPartyRetailOutlet.getPartyId(),
            selectedPartySalesman.getPartyId(),
            selectedPartyDistributor.getPartyId());


        CreateSalesRouteConfigRetailOutletInputModel in = new CreateSalesRouteConfigRetailOutletInputModel();
        in.setRetailOutletSalesmanVendorId(retailOutletSalesmanVendor.getRetailOutletSalesmanVendorId());
        in.setSalesRouteConfigId(selectedSalesRouteConfig.getSalesRouteConfigId());
        in.setSalesRoutePlanningPeriodId(selectedSalesRoutePlanningPeriod.getSalesRoutePlanningPeriodId());
        in.setStartExecuteDate("2020-01-01");

        Gson gson = new Gson();
        String json = gson.toJson(in);

        try {
            String rs = executor.execPostUseToken(
                Constants.URL_ROOT + "/api/create-sales-route-config-retail-outlet",
                json,
                token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SalesRoutePlanningCreator app = new SalesRoutePlanningCreator();
        app.run();
    }
}
