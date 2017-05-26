%额外贪心算法
function [bestAPFD,bestSuite]=AGreedy(testSuite,testCov)
%简单问题描述
%testSuite=[1,2,3,4,5];
%testCov={[1,5],[1,5,6,7],[1,2,3,4,5,6,7],[5],[8,9,10]};

%初始化参数
M=length(testSuite);%测试用例数量
hasCovered=[];%测试用例已经覆盖信息
bestSuite=[];
bestAPFD=0;
%开始优化
for i=1:M
    nowSuite=setdiff(testSuite,bestSuite);
    tmpBest=-1;
    selectNum=0;
    for j=1:length(nowSuite)  
        t=getVal(hasCovered,testCov{nowSuite(j)});
        if  t > tmpBest
            tmpBest=t;
            selectNum=nowSuite(j);
        end
    end
    if length(hasCovered) ~= 0
        newCov=setdiff(hasCovered,testCov{selectNum});
    else
         newCov=setdiff(testCov{selectNum},hasCovered);
    end
    if length(newCov)~=0
        hasCovered(length(hasCovered)+1:length(hasCovered)+length(newCov))=newCov;
    end
    bestSuite(length(bestSuite)+1)=selectNum;
end
    bestAPFD=APFD(bestSuite,testCov);
end

%获取测试用例覆盖的新信息
function newVal=getVal(hasCovered,tcCov)
interSet=intersect(hasCovered,tcCov);
leftSet=setdiff(tcCov,interSet);
newVal=length(leftSet);
end

%适应度函数
function var=APFD(testSuite,testCov)
testNewRe=[];
tmp=[];
all=[];
for i=1:1:length(testSuite)
   new=0;
   tmp=testCov{testSuite(i)};
   for j=1:1:length(tmp)
       if isempty(find(all==tmp(j)))
       %if ismember(tmp(j),all) ~= 1
           new=new+1;
           all(length(all)+1)=tmp(j);
       end
   end
   testNewRe(i)=new;
end
x=0;
y=0;
for i=1:1:length(testNewRe)
    x=x+i*testNewRe(i);
    y=y+testNewRe(i);
end
var=1-x/y/length(testNewRe)+1/2/length(testNewRe);
end