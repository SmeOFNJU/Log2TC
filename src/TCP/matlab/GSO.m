 %����DGSO�㷨�����������Ż�����
function [bestAPFD,bestSuite]=GSO(testSuite,testCov)
%����������
% testSuite=[1,2,3,4,5,6];
% testCov={[1,3],[1,2,3,5],[1,2,3,4,5,6],[2,4],[7,8,9,10],[7,8]};
% myAll=[];
% for i=1:length(testCov)
%     myAll=[myAll setdiff(testCov{i},myAll)];
% end
% myLen=length(myAll)
bestSuite=[];
bestAPFD=0;
%��ʼ������
pNum=50;%��Ⱥ��С
iterNum=100;%����������
l0=5;%�����ʼӫ��ֵ
r0=4;%��ʼ��̬������뾶
rS=20;%��֪���뾶
vola=0.4;%ӫ���ػӷ�����
upDate=0.6;%ӫ���ظ�����
rB=0.08;%��̬������뾶������
Nt=5;%�����ڵ�ө�����Ŀ��ֵ
p1=0.85;%����ʽ��ѡ�����
p2=0.9;%����ʽ��ѡ�����
c=20;%��������ϵ��
nMax=8;%�ֲ��Ż�ֹͣ����
%��ʼ������ӫ��ֵ
l=ones(1,pNum)*10;
%��ʼ�����嶯̬������뾶
rD=ones(1,pNum)*r0;
%��ʼ������·��
R={};
while length(R) < pNum
    tmpR=createIndividual(testSuite);
    if isExist(tmpR,R) ~= 1
        R{length(R)+1}=tmpR;
    end
end

a={};%����·������
nearBy={};%��������
%���е���
for iter=1:1:iterNum
    cccc=0;
    for i=1:1:pNum
        l(i)=(1-vola)*l(i)+upDate*APFD(R{i},testCov);%����ӫ����ֵ
        a{i}=enCode(R{i}); %���ȶ�·������
    end
    %%%��ɸ�������
    %����������룬�������
    A=a;%��ʱ����·������
    for i=1:1:pNum
        iNear=[];
        for j=1:1:pNum
            if i~= j
                %�����������
                if c*disTance(A{i},A{j}) <= rD(i) && l(j) > l(i)
                    iNear(length(iNear)+1)=j;
                end
            end
        end
        nearBy{i}=iNear;
        
        %%%�����ƶ�
        %�����ʴ�Сѡ���ƶ����󣬸���ө�����嶯̬������뾶
        randP=rand(1,1);
        moveObj=0;
        sumL=0;
        for j=1:1:length(nearBy{i})
            sumL=sumL+l(nearBy{i}(j));
        end
        tmpP=0;
        sumP=0;
        for j=1:1:length(nearBy{i})
            sumP=sumP+(l(nearBy{i}(j))-l(i))/(sumL-l(i));
        end
        for j=1:1:length(nearBy{i})
            tmpP=tmpP+(l(nearBy{i}(j))-l(i))/(sumL-l(i))/sumP;
            if tmpP >= randP
                moveObj=j;
                break;
            end
        end
        
        if moveObj ~= 0   
            rV=rand(1,length(testSuite));
            a{i}=Move(a,i,moveObj,p1,p2,rV);
            R{i}=decode(a{i});
        end
        %����ө�����嶯̬������뾶
        rD(i)=myMin(rS,myMax(0,rD(i)+rB*(Nt-length(nearBy(i)))));
        %��������ֵ
        tmpBest=APFD(R{i},testCov);
        if bestAPFD < tmpBest
            bestAPFD=tmpBest;
            bestSuite=R{i};
        end

           
    end
        %����3-opt���оֲ��Ż�
        t=1;
        while t<nMax
        optI=randi([1 length(bestSuite)],1,1);
        optJ=randi([1 length(bestSuite)],1,1);
        optK=randi([1 length(bestSuite)],1,1);
        maxAPFD=0;
        maxSuite=[];
        if optJ-optI >=2 && optK-optJ >=2 && optK ~= length(bestSuite)
            [sFront,sMiddle,sEnd]=mySplit(bestSuite,optI,optK);
            if ~isempty(sMiddle)
                S1=[sFront,bestSuite(optI),bestSuite(optJ+1:optK),bestSuite(optI+1:optJ),bestSuite(optK+1),sEnd];
                S2=[sFront,bestSuite(optI),bestSuite(optJ+1:optK),fliplr(bestSuite(optI+1:optJ)),bestSuite(optK+1),sEnd];
                S3=[sFront,bestSuite(optI),fliplr(bestSuite(optJ+1:optK)),bestSuite(optI+1:optJ),bestSuite(optK+1),sEnd];
                S4=[sFront,bestSuite(optI),fliplr(bestSuite(optI+1:optJ)),fliplr(bestSuite(optJ+1:optK)),bestSuite(optK+1),sEnd];
                F1=APFD(S1,testCov);
                F2=APFD(S2,testCov);
                F3=APFD(S3,testCov);
                F4=APFD(S4,testCov);
                if F1>=F2 && F1>=F3 && F1>=F4
                    maxSuite=S1;
                    maxAPFD=F1;
                end
                if F2>=F1 && F2>=F3 && F2>=F4
                    maxSuite=S2;
                    maxAPFD=F2;
                end
                if F3>=F1 && F3>=F2 && F3>=F4
                    maxSuite=S3;
                    maxAPFD=F3;
                end
                if F4>=F1 && F4>=F2 && F4>=F3
                    maxSuite=S4;
                    maxAPFD=F4;
                end
                
            end
        end
        if maxAPFD > bestAPFD
            t=1;
            bestAPFD=maxAPFD;
            bestSuite=maxSuite;
        else
            t=t+1;
        end
        end
end
end

%sub ������ʼ����
function r=createIndividual(testSuite)
r=[];
count=0;
while count < length(testSuite)
    tmp=randi([1 length(testSuite)],1,1);
    if isempty(find(r==testSuite(tmp)))
    %if ismember(testSuite(tmp),r) ~= 1
        count=count+1;
        r(count)=testSuite(tmp);
    end
end
end

%sub �ж�cell�Ƿ�����ڵ�ǰcell������
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

%sub ����APFD������Ӧ��
function var=APFD(r,testCov)
testNewRe=zeros(1,length(r));
tmp=[];
all=[];
oTimes=zeros(1,length(r));
newOver=zeros(1,length(r));
z=[];
count=[0 0 0 0];
for i=1:1:length(r)
   new=0;
   tmp=testCov{r(i)};
%    for j=1:1:length(tmp)
%        if isempty(find(all==tmp(j)))
%        %if ismember(tmp(j),all) ~= 1
%            new=new+1;
%            all(length(all)+1)=tmp(j);
%        end
%    end
   %new
   newEle=setdiff(tmp,all);
   new=length(newEle);
   all=[all newEle];
   %duplicate
   [m,n]=size(tmp);
   z=[z tmp];
   [id,s,unS]=intersect(count(:,1),tmp');
   oTimes(i)=sum(count(s,2));
   newOver(i)=length(id);
   testNewRe(i)=new;
   if length(z) <= 2
       ot=tabulate(z);
       [row,col]=size(ot);
       unUse=zeros(1,row)';
       count=[ot unUse];
   else
       count=HistRate(z);
   end
end
x=0;
y=0;
% testNewRe=testNewRe+(newOver+oTimes/sum(count(:,2)))/(length(count(:,2))+1);
testNewRe=testNewRe+oTimes/sum(count(:,2));
x=sum(sort(randperm(length(testNewRe))).*testNewRe);
y=sum(testNewRe);
var=1-x/y/length(testNewRe)+1/2/length(testNewRe);
end


%·������
function code=enCode(r)
[re,code]=sort(r(:),'ascend');
code=code';
end

%���������������
function d=disTance(ri,rj)
% for count=1:1:length(ri)
%     d=d+abs(ri(count)-rj(count));
% end
d=sum(abs(ri-rj));
if mod(length(ri),2) == 1
    M=(length(ri)-1)*(length(ri)+1)/2;
else
    M=(length(ri)-1)*length(ri)/2;
end
d=d/M;
end

%�ƶ����󣬸��±�������
function code=Move(a,i,moveObj,p1,p2,rV)
code=[];
for j=1:1:length(a{i})
    if rV(j) < p1
        a{i}(j)=a{i}(j);
    elseif rV(j) < p2
        a{i}(j)=a{moveObj}(j);
    else 
        R=randi([-1 1],1,1);
        a{i}(j)=a{i}(j)+R;
    end
end
%�������б���(��Ӱ����������)
code=modifyCode(a{i});
%�Եõ�����������2-Opt���ӽ��оֲ��Ż�������ɣ�
end



%�������б���
function oldCode=modifyCode(code)
oldCode=code;
code=sort(code);
for i=1:1:length(code)
    for j=1:1:length(oldCode)
        if code(i) == oldCode(j)
            oldCode(j)=i;
            break;
        end
    end
end
end

%�Զ���min����
function min=myMin(x,y)
if x<=y
    min=x;
else
    min=y;
end
end
%�Զ���max����
function max=myMax(x,y)
if x>=y
    max=x;
else
    max=y;
end
end

%��·���������
function path=decode(code)
[re,path]=sort(code(:),'ascend');
path=path';
end


%���������е�����
function [sFront,sMiddle,sEnd]=mySplit(S,i,j)

if 1>i-1
   sFront=[];
else
    sFront=S(1:i-1);
end

if i>j+1
    sMiddle=[];
else
    sMiddle=S(i:j+1);
end

if j+2>length(S)
    sEnd=[];
else
    sEnd=S(j+2:end);
end

end

%�����ظ���������ۼ�ֵ
function val=calOverTimes(testSuite,testCov)

end