package model;

public class Hotel {

    public static String calculateSituation(
            Double occupiedRoomsPercentage,
            Double checkInPercentage,
            Double checkOutPercentage,
            Double bookingQuantity){

        if(occupiedRoomsPercentage>0.80){                            //1
            if(checkInPercentage>checkOutPercentage){                //2
                if(bookingQuantity>0.40){                            //3
                    return "Overbooked";                             //4
                }else{
                    return "Almost overbooked";                      //5
                }

            }else if(checkInPercentage.equals(checkOutPercentage)){  //6
                if(bookingQuantity>0.40){                            //7
                    return "High risk of overbooking";               //8
                }else{
                    return "Medium risk of overbooking";             //9
                }
            }else{
                if(bookingQuantity>0.40){                            //10
                    return "Slightly risk of overbooking";           //11
                }else{
                    return "Low risk of overbooking";                //12
                }
            }

        }else if(occupiedRoomsPercentage>0.40){                      //13
            if(checkInPercentage>checkOutPercentage){                //14
                if(bookingQuantity>0.40){                            //15
                    return "Low risk of loss";                       //16
                }else{
                    return "Slightly risk of loss";                  //17
                }

            }else if(checkInPercentage.equals(checkOutPercentage)){  //18
                if(bookingQuantity>0.40){                            //19
                    return "Medium risk of loss";                    //20
                }else{
                    return "High risk of loss";                      //21
                }
            }else{
                if(bookingQuantity>0.40){                            //22
                    return "Almost loss";                            //23
                }else{
                    return "Loss";                                   //24
                }
            }
        }else{
            if(checkInPercentage>checkOutPercentage){                //25
                if(bookingQuantity>0.40){                            //26
                    return "Low risk of bankrupt";                   //27
                }else{
                    return "Slightly risk of bankrupt";              //28
                }

            }else if(checkInPercentage.equals(checkOutPercentage)){  //29
                if(bookingQuantity>0.40){                            //30
                    return "Medium risk of bankrupt";                //31
                }else{
                    return "High risk of bankrupt";                  //32
                }
            }else{
                if(bookingQuantity>0.40){                            //33
                    return "Almost bankrupt";                        //34
                }else{
                    return "Bankrupt";                               //35
                }
            }
        }                                                            //36
    }
}
