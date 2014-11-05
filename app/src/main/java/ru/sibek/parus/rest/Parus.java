package ru.sibek.parus.rest;

        import retrofit.client.Response;
        import retrofit.http.GET;
        import retrofit.http.Path;
        import ru.sibek.parus.mappers.Companies;
        import ru.sibek.parus.mappers.Invoices;
        import ru.sibek.parus.mappers.InvoicesSpec;

/**
 * Created by Developer on 06.10.2014.
 */
public interface Parus {
    /*@GET("/parus/ru/ininvoices/{company}")
    List<InInvoice> listInInvoices(@Path("company") String company);*/

    @GET("/parus/ru/ininvoices/{company}")
    Invoices listInvoices(@Path("company") String company);

    @GET("/parus/ru/invoicesspec/{NPRN}")
    InvoicesSpec invoiceSpecByNRN(@Path("NPRN") String nprn);

    @GET("/parus/ru/invoice/{NRN}")
    Invoices invoiceByNRN(@Path("NRN") String nrn);

    /*@GET("/parus/ru/ininvoices/{company}")
    Response RespInInvoices(@Path("company") String company);*/

    @GET("/parus/ru/companies/")
    Companies listCompanies();

}