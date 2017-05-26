--1、数据清洗
--alter table Fuyi drop column fmillsecond,smillsecond,messid,nodeid,orgid,operway,inputtype,logdate,core   
--delete from Fuyi where cast(custid as varchar(50)) = 'NULL' AND  cast(fundid as varchar(50)) = 'NULL'
--delete from Fuyi where funcid = 'NULL'
--delete from Fuyi where funcid like '%=%'
--delete  from Fuyi where len(funcid) <> 6

select top 100 * from Fuyi order by CAST(custid as varchar(50)), netaddr asc
select * from Fuyi where CAST(RIGHT(end_logtime,6) as int) > CAST(RIGHT(beg_logtime,6) as int)
--2、各项指标统计
select funcid,count(distinct(cast(custid as varchar(50))+cast(fundid as varchar(50))+netaddr)) as num from Fuyi group by funcid order by num desc
select cast(custid as varchar(50)) as custid,cast(fundid as varchar(50)) as fundid, netaddr, count(*) as num  from Fuyi group by  cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr order by num desc

declare @i int
set @i=10
while @i < 24
begin
--select COUNT(*) from Fuyi where beg_logtime like '20170217-0'+CAST(@i as varchar(10))+'%'
--select count(*)  from Fuyi where beg_logtime like '20170217-'+CAST(@i as varchar(10))+'%' group by  cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr
--select @@ROWCOUNT 
select funcid,count(funcid) from Fuyi where beg_logtime like '20170217-'+CAST(@i as varchar(10))+'%' group by funcid
select @@ROWCOUNT
set @i=@i+1
end



--会话分割阈值
select top 100 cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr ,beg_logtime from Fuyi order by  cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr

select * from
(
select a.id ,
	CAST(RIGHT(b.beg_logtime,6) as int)/10000*3600+CAST(RIGHT(b.beg_logtime,6) as int)%10000/100*60+CAST(RIGHT(b.beg_logtime,6) as int)%100-(CAST(RIGHT(a.beg_logtime,6) as int)/10000*3600+CAST(RIGHT(a.beg_logtime,6) as int)%10000/100*60+CAST(RIGHT(a.beg_logtime,6) as int)%100) as interval
from 
( select row_number()over(order by cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr,beg_logtime) as id,* from Fuyi) a,
( select row_number()over(order by cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr,beg_logtime) as id,* from Fuyi) b
where a.id=b.id-1 and cast(a.custid as varchar(50))= cast(b.custid as varchar(50)) and cast(a.fundid as varchar(50))= cast(b.fundid as varchar(50)) and a.netaddr=b.netaddr
)c where interval > 24 order by interval


select cast(custid as varchar(50)) as custid,cast(fundid as varchar(50)) as fundid, netaddr ,beg_logtime ,funcid from Fuyi order by  cast(custid as varchar(50)) ,cast(fundid as varchar(50)) , netaddr,beg_logtime
