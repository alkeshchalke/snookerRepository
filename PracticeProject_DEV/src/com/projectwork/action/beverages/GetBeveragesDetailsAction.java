package com.projectwork.action.beverages;

// Demonstration on the use of Streams filter(), collect(), findAny() and orElse().

public class GetBeveragesDetailsAction
{
    static int totalPrice = 0;

    public static void main(String[] args)
    {/*
        StringBuffer totalItems = new StringBuffer();

        totalItems.append("1");
        totalItems.append("2");
        totalItems.append("3");
        totalItems.append("2");
        totalItems.append("4");

        BeveragesServiceImpl beveragesServiceImplObj = new BeveragesServiceImpl();
        //List<BeveragesDetailsBean> beveragesDetailsList = beveragesServiceImplObj.getBeveragesDetails();

        if (totalItems.length() > 0)
        {
            // Find details about bought items
            List<BeveragesDetailsBean> boughtBeveragesDetailsBean = displayBeveragesDetailsBean(beveragesDetailsList, totalItems);

            // Sort the bought items based on item price(small to large)
            boughtBeveragesDetailsBean = sortBoughtItems(boughtBeveragesDetailsBean);

            System.out.println("*******************Sorted Bought Items: *******************");

            for (BeveragesDetailsBean myList : boughtBeveragesDetailsBean)
                System.out.println(myList.getItemCode() + "\t " + myList.getItemName() + "\t " + myList.getItemPrice());

            System.out.println("\nTotal Price: " + totalPrice);

            System.out.println("\n***********************************************************\n");

            int paidAmount = 50;
            payAmount(boughtBeveragesDetailsBean, paidAmount);

            System.out.println("\n***********************************************************\n");

            System.out.println("*******************Unpaid Items: *******************");

            int finalBalance = storeUnpaidItemsDetails(boughtBeveragesDetailsBean);
            
            System.out.println("\nUnpaid Balance: " + finalBalance);
        }
    */}

   /* private static int storeUnpaidItemsDetails(List<BeveragesDetailsBean> boughtBeveragesDetailsBean)
    {
        int finalBalance=0;
        for (BeveragesDetailsBean myList : boughtBeveragesDetailsBean)
        {
            System.out.println(myList.getItemCode() + "\t " + myList.getItemName() + "\t " + myList.getItemPrice());
            finalBalance+=myList.getItemPrice();
        }
        return finalBalance;
    }

    private static List<BeveragesDetailsBean> sortBoughtItems(List<BeveragesDetailsBean> boughtBeveragesDetailsBean)
    {
        Collections.sort(boughtBeveragesDetailsBean, new Comparator<BeveragesDetailsBean>()
        {
            public int compare(BeveragesDetailsBean o1, BeveragesDetailsBean o2)
            {
                int num = o2.getItemPrice() - o1.getItemPrice();

                return num;
            }
        });

        return boughtBeveragesDetailsBean;
    }

    private static void payAmount(List<BeveragesDetailsBean> boughtBeveragesDetailsBean, int paidAmount)
    {
        int balanceAmount = 0;

        Iterator<BeveragesDetailsBean> it = boughtBeveragesDetailsBean.iterator();

        while (it.hasNext())
        {
            BeveragesDetailsBean obj = it.next();

            if (paidAmount < obj.getItemPrice())
            {
                break;
            }
            balanceAmount = paidAmount - obj.getItemPrice();
            paidAmount = balanceAmount;
            System.out.println(
                    obj.getItemCode() + "\t " + obj.getItemName() + "\t " + obj.getItemPrice() + "\t " + balanceAmount);

            it.remove();
        }

        System.out.println("\nBalance Amount: " + balanceAmount);
    }

    private static List<BeveragesDetailsBean> displayBeveragesDetailsBean(List<BeveragesDetailsBean> beveragesDetailsList, StringBuffer totalItems)
    {
        String newString = totalItems.toString();
        String[] myArray = new String[totalItems.length()];
        List<BeveragesDetailsBean> boughtBeveragesDetailsBean = new ArrayList<BeveragesDetailsBean>();

        myArray = newString.split("");

        for (int i = 0; i < myArray.length; i++)
        {
            for (BeveragesDetailsBean myList : beveragesDetailsList)
            {
                if (myList.getItemCode().equals(myArray[i]))
                {
                    boughtBeveragesDetailsBean.add(myList);

                    totalPrice += myList.getItemPrice();
                    break;
                }
            }
        }

        return boughtBeveragesDetailsBean;
    }*/
}

