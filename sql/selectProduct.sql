delimiter $$
use forconsumer ;
drop procedure if exists selectProduct;
create procedure selectProduct(
  in iStart int,
  in  iNum int,
  in vown varchar(50))
begin
  if iNum is null or iNum = 0 then
    select * from product where is_visited=0 and is_valid=1 and own=vown;
  else
    select * from product where is_visited=0 and is_valid=1 and own=vown limit iStart,iNum;
  end if ;
end
$$
delimiter ;
