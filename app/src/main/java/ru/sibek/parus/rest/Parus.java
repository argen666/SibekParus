package ru.sibek.parus.rest;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import ru.sibek.parus.mappers.Cells;
import ru.sibek.parus.mappers.Companies;
import ru.sibek.parus.mappers.Racks;
import ru.sibek.parus.mappers.Status;
import ru.sibek.parus.mappers.Storages;
import ru.sibek.parus.mappers.complectations.ComplectationSpec;
import ru.sibek.parus.mappers.complectations.Complectations;
import ru.sibek.parus.mappers.ininvoices.Invoices;
import ru.sibek.parus.mappers.ininvoices.InvoicesSpec;
import ru.sibek.parus.mappers.ininvoices.Orders;
import ru.sibek.parus.mappers.ininvoices.OrdersSpec;
import ru.sibek.parus.mappers.outvoices.Nquant;
import ru.sibek.parus.mappers.outvoices.Transindept;
import ru.sibek.parus.mappers.outvoices.TransindeptSpec;
import ru.sibek.parus.mappers.outvoices.Transindepts;

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

    @GET("/parus/ru/storages/")
    Storages listStorages();

    @GET("/parus/ru/storage/{NRN}")
    Storages storageByNRN(@Path("NRN") String nrn);

    @GET("/parus/ru/racks/{NRN}")
    Racks racksByNRN(@Path("NRN") String nrn);

    @GET("/parus/ru/cells/{NRN}")
    Cells cellsByNRN(@Path("NRN") String nrn);

    @FormUrlEncoded
    @POST("/parus/ru/invoice/status")
    Status applyInvoiceAsFact(@Field("NRN") long nrn/*, @Field("last_name") String last*/);

    @GET("/parus/ru/inorders/{date}")
    Orders listOrders(@Path("date") String date);

    @GET("/parus/ru/ordersspec/{NPRN}")
    OrdersSpec orderSpecByNRN(@Path("NPRN") String nprn);

    @GET("/parus/ru/order/{NRN}")
    Orders orderByNRN(@Path("NRN") String nrn);

    @FormUrlEncoded
    @POST("/parus/ru/order/status")
    Status applyOrdereAsFact(@Field("NRN") long nrn/*, @Field("last_name") String last*/);

    @GET("/parus/ru/transindepts/{date}")
    Transindepts listTransindepts(@Path("date") String date);

    @GET("/parus/ru/transindeptspec/{NPRN}")
    TransindeptSpec transindeptSpecByNRN(@Path("NPRN") String nprn);

    @DELETE("/parus/ru/transindeptspec/{NPRN}")
    Status deleteTransindeptSpecByNRN(@Path("NPRN") String nprn);

    @GET("/parus/ru/transindept/{NRN}")
    Transindepts transindeptByNRN(@Path("NRN") String nrn);

    @PUT("/parus/ru/transindept/")
    Status addTransindept(@Body Transindept json);

    @FormUrlEncoded
    @POST("/parus/ru/transindept/")
    Status applyTransindeptAsFactWithIncome(@Field("NRN") long nrn);

    @FormUrlEncoded
    @POST("/parus/ru/transindeptspec/{NPRN}")
    Status addTransindeptSpecByMasterNRN(@Path("NPRN") long nrn, @Field("NNOMEN") String nnomen);

    @PUT("/parus/ru/transindeptspec/{NPRN}")
    Status updateTransindeptSpecNQuant(@Path("NPRN") long nprn, @Body Nquant json);

    @GET("/parus/ru/complectations/{date}")
    Complectations listComplectations(@Path("date") String date);

    @GET("/parus/ru/complectation/{NRN}")
    Complectations complectationByNRN(@Path("NRN") String nrn);

    @GET("/parus/ru/complectationspec/{NPRN}")
    ComplectationSpec complectationSpecByNRN(@Path("NPRN") String nprn);

    @PUT("/parus/ru/complectationspec/{NPRN}")
    Status complectationSpecComplect(@Path("NPRN") long nprn, @Body Nquant json);

    @POST("/parus/ru/complectationspec/{NPRN}")
    Status createTransindeptByComplectation(@Path("NPRN") long complectationNRN);
}