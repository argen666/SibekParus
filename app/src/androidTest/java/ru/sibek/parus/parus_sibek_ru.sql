set define off
set verify off
set feedback off
WHENEVER SQLERROR EXIT SQL.SQLCODE ROLLBACK
begin wwv_flow.g_import_in_progress := true; end;
/
 
--       AAAA       PPPPP   EEEEEE  XX      XX
--      AA  AA      PP  PP  EE       XX    XX
--     AA    AA     PP  PP  EE        XX  XX
--    AAAAAAAAAA    PPPPP   EEEE       XXXX
--   AA        AA   PP      EE        XX  XX
--  AA          AA  PP      EE       XX    XX
--  AA          AA  PP      EEEEEE  XX      XX
prompt  Set Credentials...
 
begin
 
  -- Assumes you are running the script connected to SQL*Plus as the Oracle user APEX_040200 or as the owner (parsing schema) of the application.
  wwv_flow_api.set_security_group_id(p_security_group_id=>nvl(wwv_flow_application_install.get_workspace_id,2223106859552321));
 
end;
/

begin wwv_flow.g_import_in_progress := true; end;
/
begin 

select value into wwv_flow_api.g_nls_numeric_chars from nls_session_parameters where parameter='NLS_NUMERIC_CHARACTERS';

end;

/
begin execute immediate 'alter session set nls_numeric_characters=''.,''';

end;

/
begin wwv_flow.g_browser_language := 'en'; end;
/
prompt  Check Compatibility...
 
begin
 
-- This date identifies the minimum version required to import this file.
wwv_flow_api.set_version(p_version_yyyy_mm_dd=>'2012.01.01');
 
end;
/

prompt  Set Application Offset...
 
begin
 
   -- SET APPLICATION OFFSET
   wwv_flow_api.g_id_offset := nvl(wwv_flow_application_install.get_offset,0);
null;
 
end;
/

 
begin
 
wwv_flow_api.remove_restful_service (
  p_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_name => 'parus.sibek.ru'
  );
 
null;
 
end;
/

prompt  ...restful service
--
--application/restful_services/parus_sibek_ru
 
begin
 
wwv_flow_api.create_restful_module (
  p_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_name => 'parus.sibek.ru'
 ,p_uri_prefix => 'ru/'
 ,p_parsing_schema => 'PARUS'
 ,p_items_per_page => 25
 ,p_status => 'PUBLISHED'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2369427704312971 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'cells/{NPRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2369503247315292 + wwv_flow_api.g_id_offset
 ,p_template_id => 2369427704312971 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select CEL.sfullnumber from V_STPLCELLS CEL where NPRN=:NPRN'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2339926567109244 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'companies/'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2340032108110838 + wwv_flow_api.g_id_offset
 ,p_template_id => 2339926567109244 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select * from COMPANIES'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2347020946020511 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'ininvoices/'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2347131335023508 + wwv_flow_api.g_id_offset
 ,p_template_id => 2347020946020511 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select * from V_ININVOICES'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2340127737119017 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'ininvoices/{DATE}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2340202241121147 + wwv_flow_api.g_id_offset
 ,p_template_id => 2340127737119017 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'/*select NRN,NCOMPANY,SJUR_PERS,NDOCTYPE,SDOCTYPE,SPREF,SNUMB,'||unistr('\000a')||
'to_char(DDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,NSTATUS,SSTATUS,'||unistr('\000a')||
'DWORK_DATE,SAGENT,SAGENT_NAME,NSUMM,NSUMMTAX from V_ININVOICES'||unistr('\000a')||
'where NCOMPANY= :COMPANY*/'||unistr('\000a')||
''||unistr('\000a')||
'select distinct V.NRN,V.NCOMPANY,V.SJUR_PERS,V.NDOCTYPE,V.SDOCTYPE,V.SPREF,V.SNUMB,'||unistr('\000a')||
'to_char(V.DDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,V.NSTATUS,SSTATUS,'||unistr('\000a')||
'V.DWORK_DATE,V.S'||
'AGENT,V.SAGENT_NAME,V.NSUMM,V.NSUMMTAX,M.dmodifdate from V_ININVOICES V'||unistr('\000a')||
'LEFT JOIN'||unistr('\000a')||
'('||unistr('\000a')||
'SELECT distinct s1.*'||unistr('\000a')||
'FROM V_UPDATELIST s1'||unistr('\000a')||
'JOIN ('||unistr('\000a')||
'    SELECT ntablern,MAX(dmodifdate) AS dmodifdate'||unistr('\000a')||
'    FROM V_UPDATELIST'||unistr('\000a')||
'    GROUP BY ntablern'||unistr('\000a')||
') S2 ON s1.ntablern = s2.ntablern AND s1.dmodifdate = s2.dmodifdate --where s1.ntablern=3583310;'||unistr('\000a')||
') M'||unistr('\000a')||
'ON'||unistr('\000a')||
'M.ntablern=V.nrn '||unistr('\000a')||
'where M.dmodifdate > (date ''1970-01-01'' + 0.001*:DA'||
'TE/60/60/24)'||unistr('\000a')||
'OR M.dmodifdate is null and V.ncompany=59945'||unistr('\000a')||
'order by M.dmodifdate desc '
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2381002737812854 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'inorders/{DATE}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2381108278814418 + wwv_flow_api.g_id_offset
 ,p_template_id => 2381002737812854 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select distinct V.NRN,V.NCOMPANY,V.SJUR_PERS,V.NINDOCTYPE as NDOCTYPE,V.SINDOCTYPE as SDOCTYPE,V.SINDOCPREF as SPREF,V.SINDOCNUMB as SNUMB,V.nstore,V.sstore,'||unistr('\000a')||
'to_char(V.DINDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,V.NDOCSTATUS as NSTATUS,V.SDOCSTATUS as SSTATUS,'||unistr('\000a')||
'V.DWORK_DATE,V.SAGENT,V.nfactsum as NSUMM,V.nfactsumtax as NSUMMTAX,M.dmodifdate from V_INORDERS V'||unistr('\000a')||
'LEFT JOIN'||unistr('\000a')||
'('||unistr('\000a')||
'SELECT distinct s1.*'||unistr('\000a')||
'FRO'||
'M V_UPDATELIST s1'||unistr('\000a')||
'JOIN ('||unistr('\000a')||
'    SELECT ntablern,MAX(dmodifdate) AS dmodifdate'||unistr('\000a')||
'    FROM V_UPDATELIST'||unistr('\000a')||
'    GROUP BY ntablern'||unistr('\000a')||
') S2 ON s1.ntablern = s2.ntablern AND s1.dmodifdate = s2.dmodifdate --where s1.ntablern=3583310;'||unistr('\000a')||
') M'||unistr('\000a')||
'ON'||unistr('\000a')||
'M.ntablern=V.nrn '||unistr('\000a')||
'where M.dmodifdate > (date ''1970-01-01'' + 0.001*:DATE/60/60/24)'||unistr('\000a')||
'OR M.dmodifdate is null and V.ncompany=59945'||unistr('\000a')||
'order by M.dmodifdate desc '
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2373622003897680 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'invoice/status'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2373729622899819 + wwv_flow_api.g_id_offset
 ,p_template_id => 2373622003897680 + wwv_flow_api.g_id_offset
 ,p_source_type => 'PLSQL'
 ,p_format => 'DEFAULT'
 ,p_method => 'POST'
 ,p_mimes_allowed => 'application/x-www-form-urlencoded'
 ,p_require_https => 'NO'
 ,p_source => 
'declare '||unistr('\000a')||
'  NRN NUMBER;'||unistr('\000a')||
'  RESULT varchar2(10);'||unistr('\000a')||
'N float;'||unistr('\000a')||
'M varchar2(10);'||unistr('\000a')||
'begin'||unistr('\000a')||
'P_ININVOICES_SET_STATUS(59945, :NRN, 2, TO_DATE(''24.11.2014'', ''DD.MM.YYYY HH24:MI:SS''), N, M);'||unistr('\000a')||
'RESULT:=''OK'';'||unistr('\000a')||
'end;'
  );
 
wwv_flow_api.create_restful_param (
  p_id => 2373824568983577 + wwv_flow_api.g_id_offset
 ,p_handler_id => 2373729622899819 + wwv_flow_api.g_id_offset
 ,p_name => 'NRN'
 ,p_bind_variable_name => 'NRN'
 ,p_source_type => 'HEADER'
 ,p_access_method => 'IN'
 ,p_param_type => 'INT'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2348314232601476 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'invoice/{NRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2348418734602808 + wwv_flow_api.g_id_offset
 ,p_template_id => 2348314232601476 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'/*select NRN,NCOMPANY,SJUR_PERS,NDOCTYPE,SDOCTYPE,SPREF,SNUMB,'||unistr('\000a')||
'to_char(DDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,NSTATUS,SSTATUS,'||unistr('\000a')||
'DWORK_DATE,SAGENT,SAGENT_NAME,NSUMM,NSUMMTAX'||unistr('\000a')||
'from V_ININVOICES where nrn=:NRN*/'||unistr('\000a')||
''||unistr('\000a')||
'select distinct V.NRN,V.NCOMPANY,V.SJUR_PERS,V.NDOCTYPE,V.SDOCTYPE,V.SPREF,V.SNUMB,'||unistr('\000a')||
'to_char(V.DDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,V.NSTATUS,SSTATUS,'||unistr('\000a')||
'V.DWORK_DATE,V.SAGENT,V.SA'||
'GENT_NAME,V.NSUMM,V.NSUMMTAX,'||unistr('\000a')||
'to_char(M.dmodifdate,''yyyy-MM-dd HH:MI:SS'') as dmodifdate'||unistr('\000a')||
'from V_ININVOICES V'||unistr('\000a')||
'LEFT JOIN'||unistr('\000a')||
'('||unistr('\000a')||
'SELECT distinct s1.*'||unistr('\000a')||
'FROM V_UPDATELIST s1'||unistr('\000a')||
'JOIN ('||unistr('\000a')||
'    SELECT ntablern,MAX(dmodifdate) AS dmodifdate'||unistr('\000a')||
'    FROM V_UPDATELIST'||unistr('\000a')||
'    GROUP BY ntablern'||unistr('\000a')||
') S2 ON s1.ntablern = s2.ntablern AND s1.dmodifdate = s2.dmodifdate --where s1.ntablern=3583310;'||unistr('\000a')||
') M'||unistr('\000a')||
'ON'||unistr('\000a')||
'M.ntablern=V.nrn '||unistr('\000a')||
'where V.NRN=:NR'||
'N and V.ncompany=59945'||unistr('\000a')||
'order by dmodifdate desc '
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2347830883076746 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'invoicesspec/{NPRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2347900886077586 + wwv_flow_api.g_id_offset
 ,p_template_id => 2347830883076746 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'/*select INV.SNOMEN, INV.SNOMENNAME, INV.SSERNUMB, INV.NQUANT, INV.NPRICE,INV.NSUMMTAX,INV.SSTORE,INV.SNOTE, INV.SMEAS_MAIN, ST.ndistribution_sign'||unistr('\000a')||
'from V_ININVOICESSPECS INV, V_DICSTORE ST  where NPRN =:NPRN and INV.SSTORE=ST.snumb*/'||unistr('\000a')||
''||unistr('\000a')||
'select INV.nrn, INV.nprn, INV.SNOMEN, INV.SNOMENNAME, INV.SSERNUMB, INV.NQUANT, INV.NPRICE,INV.NSUMMTAX,INV.NSTORE,INV.SSTORE,ST.ndistribution_sign, INV.SNOTE, INV.S'||
'MEAS_MAIN, trim(V_DIST.SRACK_PREF)||trim(V_DIST.SRACK_NUMB) AS SRACK,'||unistr('\000a')||
'V_DIST.SFULLNUMBER AS SCELL, V_DIST.NRACK'||unistr('\000a')||
'from V_ININVOICESSPECS INV LEFT JOIN '||unistr('\000a')||
'('||unistr('\000a')||
'select  S.NPRN AS NRACK,S.SRACK_PREF,S.SRACK_NUMB, S.SFULLNUMBER, substr(F_DOCS_PROPS_GET_STR_VALUE(3486654, ''StoragePlacesCells'', NRN), 1, 240) SNOMENNAME, '||unistr('\000a')||
'substr(F_DOCS_PROPS_GET_STR_VALUE(3486731, ''StoragePlacesCells'', NRN), 1, 240) SMODIFNAME'||unistr('\000a')||
''||
'from V_STPLCELLS S where NPRN IN (select NRN from V_STPLRACKS M where NSTORE IN (select NSTORE from V_ININVOICESSPECS S where NPRN=:NPRN and NCOMPANY=59945))'||unistr('\000a')||
') '||unistr('\000a')||
'V_DIST ON INV.snomenname=V_DIST.SNOMENNAME  LEFT JOIN V_DICSTORE ST ON INV.SSTORE=ST.snumb'||unistr('\000a')||
'where NPRN =:NPRN ORDER BY SNOMENNAME'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2382628441478729 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'order/status'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2382731689489103 + wwv_flow_api.g_id_offset
 ,p_template_id => 2382628441478729 + wwv_flow_api.g_id_offset
 ,p_source_type => 'PLSQL'
 ,p_format => 'DEFAULT'
 ,p_method => 'POST'
 ,p_mimes_allowed => 'application/x-www-form-urlencoded'
 ,p_require_https => 'NO'
 ,p_source => 
'declare '||unistr('\000a')||
'  NRN NUMBER;'||unistr('\000a')||
'  RESULT varchar2(10);'||unistr('\000a')||
'N float;'||unistr('\000a')||
'M varchar2(10);'||unistr('\000a')||
'begin'||unistr('\000a')||
'/*P_ININVOICES_SET_STATUS(59945, :NRN, 2, TO_DATE(''24.11.2014'', ''DD.MM.YYYY HH24:MI:SS''), N, M);*/'||unistr('\000a')||
'P_INORDERS_SET_STATUS(59945, :NRN, 2, TO_DATE(''10.02.2015'','||unistr('\000a')||
' ''DD.MM.YYYY HH24:MI:SS''), N, M);'||unistr('\000a')||
'RESULT:=''OK'';'||unistr('\000a')||
'end;'
  );
 
wwv_flow_api.create_restful_param (
  p_id => 2382808964492022 + wwv_flow_api.g_id_offset
 ,p_handler_id => 2382731689489103 + wwv_flow_api.g_id_offset
 ,p_name => 'NRN'
 ,p_bind_variable_name => 'NRN'
 ,p_source_type => 'HEADER'
 ,p_access_method => 'IN'
 ,p_param_type => 'INT'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2382113947643836 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'order/{NRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2382221220645915 + wwv_flow_api.g_id_offset
 ,p_template_id => 2382113947643836 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select distinct V.NRN,V.NCOMPANY,V.SJUR_PERS,V.NINDOCTYPE as NDOCTYPE,V.SINDOCTYPE as SDOCTYPE,V.SINDOCPREF as SPREF,V.SINDOCNUMB as SNUMB,V.nstore,V.sstore,'||unistr('\000a')||
'to_char(V.DINDOC_DATE,''yyyy-MM-dd HH:MI:SS'') as DDOC_DATE,V.NDOCSTATUS as NSTATUS,V.SDOCSTATUS as SSTATUS,'||unistr('\000a')||
'V.DWORK_DATE,V.SAGENT,V.nfactsum as NSUMM,V.nfactsumtax as NSUMMTAX,M.dmodifdate'||unistr('\000a')||
'from V_INorders V'||unistr('\000a')||
'LEFT JOIN'||unistr('\000a')||
'('||unistr('\000a')||
'SELECT distinct s1.*'||unistr('\000a')||
'FRO'||
'M V_UPDATELIST s1'||unistr('\000a')||
'JOIN ('||unistr('\000a')||
'    SELECT ntablern,MAX(dmodifdate) AS dmodifdate'||unistr('\000a')||
'    FROM V_UPDATELIST'||unistr('\000a')||
'    GROUP BY ntablern'||unistr('\000a')||
') S2 ON s1.ntablern = s2.ntablern AND s1.dmodifdate = s2.dmodifdate --where s1.ntablern=3583310;'||unistr('\000a')||
') M'||unistr('\000a')||
'ON'||unistr('\000a')||
'M.ntablern=V.nrn '||unistr('\000a')||
'where V.NRN=:NRN and V.ncompany=59945'||unistr('\000a')||
'order by dmodifdate desc '
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2381802680126591 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'ordersspec/{NPRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2381915494130356 + wwv_flow_api.g_id_offset
 ,p_template_id => 2381802680126591 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select INV.nrn, INV.nprn, INV.SNOMEN, INV.SNOMENNAME,INV.NPLANQUANT,INV.NFACTQUANT,/* INV.SSERNUMB, INV.NQUANT, INV.NPRICE,INV.NSUMMTAX,INV.NSTORE,INV.SSTORE,ST.ndistribution_sign, INV.SNOTE, */INV.SMEAS_MAIN, trim(V_DIST.SRACK_PREF)||trim(V_DIST.SRACK_NUMB) AS SRACK,'||unistr('\000a')||
'V_DIST.SCELL_CODE AS SCELL, V_DIST.NRACK'||unistr('\000a')||
'from v_inorderspecs INV LEFT JOIN '||unistr('\000a')||
'('||unistr('\000a')||
'select V_STRPLRESJRNL_DOCS.* from V_STRPLRESJRNL_DOCS'||
' where NRES_TYPE = 0 and exists (select * from V_DOCLINKS_INOUT_IN_EXT DLIN'||unistr('\000a')||
' where (DLIN.NIN_DOCUMENT=:NPRN) and (DLIN.SIN_UNITCODE=''IncomingOrdersSpecs'') and (DLIN.NDOCUMENT=NRN))  order by DRESERVING_DATE'||unistr('\000a')||
') '||unistr('\000a')||
'V_DIST ON INV.snomen=V_DIST.SNOMEN'||unistr('\000a')||
'where NPRN =:NPRN ORDER BY SNOMENNAME'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2369016174109304 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'racks/{NSTORE}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2369123792111546 + wwv_flow_api.g_id_offset
 ,p_template_id => 2369016174109304 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select RK.NRN, RK.SFULLNUMBER from V_STPLRACKS RK where NSTORE=:NSTORE'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2368816829806780 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'storage/{NRN}'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2368923755808737 + wwv_flow_api.g_id_offset
 ,p_template_id => 2368816829806780 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select DIC.NRN, DIC.SNUMB, DIC.ndistribution_sign, DIC.SNAME from V_DICSTORE DIC where DIC.NRN=:NRN'
  );
 
wwv_flow_api.create_restful_template (
  p_id => 2368422355206215 + wwv_flow_api.g_id_offset
 ,p_module_id => 2339821718107828 + wwv_flow_api.g_id_offset
 ,p_uri_template => 'storages/'
 ,p_priority => 0
 ,p_etag_type => 'HASH'
  );
 
wwv_flow_api.create_restful_handler (
  p_id => 2368530320208492 + wwv_flow_api.g_id_offset
 ,p_template_id => 2368422355206215 + wwv_flow_api.g_id_offset
 ,p_source_type => 'QUERY'
 ,p_format => 'DEFAULT'
 ,p_method => 'GET'
 ,p_items_per_page => 0
 ,p_require_https => 'NO'
 ,p_source => 
'select DIC.NRN, DIC.SNUMB, DIC.ndistribution_sign, DIC.SNAME from V_DICSTORE DIC'||unistr('\000a')||
'-- where DIC.ncompany=:COMPANY'
  );
 
null;
 
end;
/

commit;
begin
execute immediate 'begin sys.dbms_session.set_nls( param => ''NLS_NUMERIC_CHARACTERS'', value => '''''''' || replace(wwv_flow_api.g_nls_numeric_chars,'''''''','''''''''''') || ''''''''); end;';
end;
/
set verify on
set feedback on
set define on
prompt  ...done
