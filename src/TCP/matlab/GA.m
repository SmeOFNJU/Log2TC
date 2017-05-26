%利用GA算法解决测试用例序列优化问题
function [bestVar,bestSuite]=GA(testSuite,testCov)
%简单问题描述
%testSuite=[1,2,3,4,5];
%testCov={[1,5],[1,5,6,7],[1,2,3,4,5,6,7],[5],[8,9,10]};
bestSuite=[];
bestVar=0;
%预设参数
S=50;%种群个体数目
gMax=100;%迭代次数
Pc=0.8;%交叉概率
Pm=0.1;%变异概率
%创建初始种群
R0={};
while length(R0)<S
    T=createRanIndividual(testSuite);
    if isExist(T,R0) ~= 1
        R0{length(R0)+1}=T;
    end
end

%开始
Rg={};
F=[];
for g=1:1:gMax
    %计算个体适应度
    F=[];
    for i=1:length(R0)
        F(i)=CalFitness(R0{i},testCov);
    end
    tmpBest=keep_best(F,R0);
    %开始进化
    Rg={};
    while length(Rg) < S
        inK=SelectParents(R0,F);
        inL=SelectParents(R0,F);
        [inQ,inR]=ApplyCrossOver(inK,inL,Pc);
        inQ=ApplyMutation(inQ,Pm);
        inR=ApplyMutation(inR,Pc);
        Rg{length(Rg)+1}=inQ;
        Rg{length(Rg)+1}=inR;
    end
    %保留上一种群最好个体
    Rg{S+1}=tmpBest;
    %迭代
    R0=Rg;  
end

%找出最优个体
[bestVar,bestSuite]=FindMaxFitnessSuite(Rg,F);
end


function [T]=createRanIndividual(testSuite)
T=[];
count=0;
while count < length(testSuite)
    tmp=randi([1 length(testSuite)],1,1);
    if isempty(find(T==testSuite(tmp)))
        count=count+1;
        T(count)=testSuite(tmp);
    end
end
end

function b=isExist(var,cellArray)
b=0;
for i=1:1:length(cellArray)
    if cellArray{i} == var
        b=1;
        break;
    else
        continue;
    end
end
end


function [f]=CalFitness(testSuite,testCov)
%APFD
testNewRe=[];
tmp=[];
all=[];
for i=1:1:length(testSuite)
   new=0;
   tmp=testCov{testSuite(i)};
%    for j=1:1:length(tmp)
%        if isempty(find(all==tmp(j)))
%        %if ismember(tmp(j),all) ~= 1
%            new=new+1;
%            all(length(all)+1)=tmp(j);
%        end
%    end
   newEle=setdiff(tmp,all);
   new=length(newEle);
   all=[all newEle];
   testNewRe(i)=new;
end
x=0;
y=0;
for i=1:1:length(testNewRe)
    x=x+i*testNewRe(i);
    y=y+testNewRe(i);
end
f=1-x/y/length(testNewRe)+1/2/length(testNewRe);
end

function [tmpBest]=keep_best(F,R0)
tmpBest=[];
tmpMax=0;
best=0;
for i=1:length(F)
    if tmpMax < F(i)
        tmpMax=F(i);
        best=i;
    end
end
tmpBest=R0{i};
end


function inD=SelectParents(R0,F)
%轮盘赌策略选择
randNum=rand(1,1);
sumF=0;
for i=1:length(F)
    sumF=sumF+F(i);
end
tmpF=0;
for i=1:length(F)
   tmpF=tmpF+F(i)/sumF;
   if tmpF >= randNum
       inD=R0{i};
       break;
   end
end
end


function [inQ,inR]=ApplyCrossOver(inK,inL,Pc)%多个数组返回
randDo=rand(1,1);
if randDo >= Pc
    inQ=inK;
    inR=inL;
    return;
end
n=length(inK);
randK=randi([2 n-1],1,1);
inQ=inK(1:randK);
inR=inL(1:n-randK);
for i=1:n
    if isempty(find(inQ==inL(i)))
        inQ(length(inQ)+1)=inL(i);
    end
end

for j=1:n
    if isempty(find(inR==inK(j)))
        inR(length(inR)+1)=inK(j);
    end   
end
end


function [inD]=ApplyMutation(oriIn,Pm)
ranMu=rand(1,1);
if ranMu >= Pm
    inD=oriIn;
    return;
end
randPos1=randi([1 length(oriIn)],1,1);
randPos2=randi([1 length(oriIn)],1,1);
tmp=0;
tmp=oriIn(randPos1);
oriIn(randPos1)=oriIn(randPos2);
oriIn(randPos2)=tmp;
inD=oriIn;
end

function [bestVar,bestSuite]=FindMaxFitnessSuite(Rg,F)
bestVar=0;
best=0;
for i=1:length(F)
    if bestVar < F(i)
        bestVar=F(i);
        best=i;
    end
end
bestSuite=Rg{best};
end