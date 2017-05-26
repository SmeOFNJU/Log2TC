%计算平均测试用例错误值
function [val,apotfdRe,apofdRe]=CalAvgWeiError(resultAPOTFDFile,resultAPOFDFilecovFile,covFile,outFileOT,outFileOri)
val=0;
apotfdRe=0;
apofdRe=0;
fOT=fopen(outFileOT,'w');
fOri=fopen(outFileOri,'w');
fin=fopen(resultAPOTFDFile,'r');
count=0;
TC={};
while feof(fin)==0
    str=fgetl(fin);
    if ~isempty(regexp(str,'bestSuite','match'))
        count=count+1;
        str=regexp(str,':','split');
        tc=regexp(str{2},',','split');
        TC{count}=tc;
    end
end
fclose(fin);

fin=fopen(resultAPOFDFilecovFile,'r');
count=0;
oriTC={};
while feof(fin)==0
    str=fgetl(fin);
    if ~isempty(regexp(str,'bestSuite','match'))
        count=count+1;
        str=regexp(str,':','split');
        tc=regexp(str{2},',','split');
        oriTC{count}=tc;
    end
end
fclose(fin);
%
fCov=fopen(covFile,'r');
testSuite=[];
testSuite2=[];
testOriSuite=[];
testCov={};
count=0;
while feof(fCov)==0
    str=fgetl(fin);
        %获取测试用例
        if ~isempty(regexp(str,'suite','match'))
            testSuite=[];
            testSuite2=[];
            testOriSuite=[];
            testCov={};
            count=count+1;
        end
        s=regexp(str,':','split');
        if ~isempty(s) && length(s)>1
            m=regexp(s(2),',','split');
            if ~isempty(m)
                m=m{1,1};
                for i=1:1:(length(m)-1)
                    testOriSuite(i)=str2double(m(i));
                end
            end
        end 
        
        %获取测试用例的覆盖率信息
        s=regexp(str,'\|','split');
        for j=1:1:(length(s)-1)
            tmp=regexp(s{j},',','split');
            tmpCov=[];
            for k=1:1:(length(tmp)-1)
                tmpCov(k)=str2num(tmp{1,k});
            end
            testCov{j}=tmpCov;
        end
        %
        if length(testCov)>0 && length(testOriSuite)>0
            %APDTFD
            myTC=zeros(1,length(TC{1,count}));
            for k=1:length(myTC)
                myTC(k)=str2num(TC{1,count}{k});
            end
            for k=1:length(myTC)
                for i=1:length(myTC)
                    if(myTC(k)==testOriSuite(i))
                        testSuite(k)=i;
                    end
                end
            end
            
            %APFD
             myOriTC=zeros(1,length(oriTC{1,count}));
            for k=1:length(myOriTC)
                myOriTC(k)=str2num(oriTC{1,count}{k});
            end
            for k=1:length(myOriTC)
                for i=1:length(myOriTC)
                    if(myOriTC(k)==testOriSuite(i))
                        testSuite2(k)=i;
                    end
                end
            end
            
            [newx,newOverx,OTimesx]=CalAvgWeiErr(testSuite,testCov);
            [newy,newOvery,OTimesy]=CalAvgWeiErr(testSuite2,testCov); 
            if newOverx==newOvery || abs(newOverx-newOvery) <= 5
%                 if isequal(newOverx,newOvery)
%                 if abs(reX-reY)<=2 %去除特殊值的影响
                    val=val+1;
                    apotfdRe=apotfdRe+OTimesx;
                    apofdRe=apofdRe+OTimesy;
                    fprintf(fOT,'OT:%d\n',OTimesx);
                    fprintf(fOri,'Ori:%d\n',OTimesy);
%                 else
%                     zzx=0;
%                 end
%                 end
                
            end
        end
end
fclose(fOT);
fclose(fOri);
fclose(fCov);
apotfdRe=apotfdRe/val;
apofdRe=apofdRe/val;
end

function [testNewRe,newRe,re]=CalAvgWeiErr(r,testCov)
testNewRe=zeros(1,length(r));
tmp=[];
all=[];
oTimes=zeros(1,length(r));
newOver=zeros(1,length(r));
z=[];
count=[0 0 0];
for i=1:1:length(testNewRe)
   new=0;
   tmp=testCov{r(i)};
   newEle=setdiff(tmp,all);
   new=length(newEle);
   all=[all newEle];
   %duplicate
   [m,n]=size(tmp);
   z=[z tmp];
   id=intersect(count(:,1),tmp');
   s=zeros(1,length(id));
   for q=1:length(id)
       s(q)=count(count(:,1)==id(q),2);
   end
   oTimes(i)=sum(s);
   newOver(i)=length(id);
   testNewRe(i)=new;
   count=tabulate(z(:));
end
re=0;
newRe=0;
for i=1:length(oTimes)
    re=re+i*oTimes(i)/sum(count(:,2));
    newRe=newRe+i*testNewRe(i);
end
end