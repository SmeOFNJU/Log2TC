function main()
% [data,frequency] = clusterData();
% variAnce=[];
% for k=2:10
%     U=NJW(data,k);
%     [IDX,C] = kmedoids(U,k,'Distance','cosine');
%     variAnce=[variAnce getVAR(IDX,data,k)];
% end

k=[2 3 4 5 6 7 8 9 10];
variAnce=[3 2.45 2.25 1.96 1.93 1.75 1.6 1.55 1.53];
plot(k,variAnce);
xlabel('聚类簇数','Fontname','Times New Roman','FontSize',9);
ylabel('簇内方差均值','Fontname','Times New Roman','FontSize',9);
set(gca,'Fontname','Monospaced','FontSize',9);
% U=NJW(data,5);
% [IDX,C] = kmedoids(U,5,'Distance','cosine');
end


function var=getVAR(IDX,U,k)
var=0;
for i=1:k
    location=find(IDX==i);
    a=U(location,:);
    for j=1:size(a,2)
        var=var+std(a(:,j))^2;
    end    
end
% var=var/k;
var=sqrt(var/k);
end
