package ru.sibek.parus;

        import retrofit.client.Response;
        import retrofit.http.GET;
        import retrofit.http.Path;

/**
 * Created by Developer on 06.10.2014.
 */
public interface Parus {
    /*@GET("/parus/ru/ininvoices/{company}")
    List<InInvoice> listInInvoices(@Path("company") String company);*/

    @GET("/parus/ru/ininvoices/{company}")
    Invoices listInv(@Path("company") String company);

    @GET("/parus/ru/ininvoices/{company}")
    Response RespInInvoices(@Path("company") String company);

   /* @GET("/parus/ru/companies/")
    List<InInvoice> listInInvoices();*/

}