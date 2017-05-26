function U=NJW(data,k)
%%NJW�㷨  ѡȡ���Ͼ����ǰK���������ֵ��Ӧ������������ʹ����R��k���ռ��й�����ԭ����һһ��Ӧ�ı��������ڸÿռ��ڽ��о���
%%data n*m n��������  m ά��
%% k  ѡ��ǰk���������ֵ��Ӧ����������

%�������ƾ���
affinity = CalculateAffinity(data);
% ����ԽǾ���
D=eye(size(affinity,1));
for i=1:size(affinity,1)
    D(i,i) = sum(affinity(i,:));
end

%����������˹���󣬲��÷ǹ淶������
% L=D-affinity;

% �淶��
for i=1:size(affinity,1)
    for j=1:size(affinity,2)
        L(i,j) = affinity(i,j) / (sqrt(D(i,i)) * sqrt(D(j,j)));  
    end
end


%��������ֵ��������
[eigVectors,eigValues] = eig(L);

% ѡȡǰK���������ֵ
[eigValues, ind] = sort(diag(eigValues), 'descend');

nEigVec =eigVectors(:,ind(1:k));

% �����һ������U�ӻ�õ���������
U=zeros(size(nEigVec,1),k);
for i=1:size(nEigVec,1)
    n = sqrt(sum(nEigVec(i,:).^2));    
    U(i,:) = nEigVec(i,:) ./ n; 
end

end

function [affinity] = CalculateAffinity(data)

sigma = getSigma(data);
for i=1:size(data,1)
%     a=ones(size(data,1),1)*data(i,:)-data;ŷʽ����
    a=ones(size(data,1),1)*data(i,:);%���Ҿ���
    for j=1:size(data,1)
%         dist=norm(a(j,:));ŷʽ����
          dist=dot(a(j,:),data(j,:))/(norm(a(j,:))*norm(data(j,:)));%���Ҿ���
          dist=1/(2+dist);%���Ҿ���
        affinity(i,j)= exp(-dist/(2*sigma^2)); 
    end
end
end

function sigma=getSigma(data)
%�����������ݵı�׼����Ϊ���Ų���
sigma=0;
for i=1:size(data,2)
    sigma=sigma+std(data(:,i))^2;
end
sigma=sqrt(sigma);
end