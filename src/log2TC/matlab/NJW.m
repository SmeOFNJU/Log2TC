function U=NJW(data,k)
%%NJW算法  选取拉氏矩阵的前K个最大特征值对应的特征向量，使其在R（k）空间中构成与原数据一一对应的表述，并在该空间内进行聚类
%%data n*m n样本个数  m 维度
%% k  选择前k个最大特征值对应的特征向量

%计算相似矩阵
affinity = CalculateAffinity(data);
% 计算对角矩阵
D=eye(size(affinity,1));
for i=1:size(affinity,1)
    D(i,i) = sum(affinity(i,:));
end

%计算拉普拉斯矩阵，采用非规范化矩阵
% L=D-affinity;

% 规范化
for i=1:size(affinity,1)
    for j=1:size(affinity,2)
        L(i,j) = affinity(i,j) / (sqrt(D(i,i)) * sqrt(D(j,j)));  
    end
end


%计算特征值特征向量
[eigVectors,eigValues] = eig(L);

% 选取前K个最大特征值
[eigValues, ind] = sort(diag(eigValues), 'descend');

nEigVec =eigVectors(:,ind(1:k));

% 构造归一化矩阵U从获得的特征向量
U=zeros(size(nEigVec,1),k);
for i=1:size(nEigVec,1)
    n = sqrt(sum(nEigVec(i,:).^2));    
    U(i,:) = nEigVec(i,:) ./ n; 
end

end

function [affinity] = CalculateAffinity(data)

sigma = getSigma(data);
for i=1:size(data,1)
%     a=ones(size(data,1),1)*data(i,:)-data;欧式距离
    a=ones(size(data,1),1)*data(i,:);%余弦距离
    for j=1:size(data,1)
%         dist=norm(a(j,:));欧式距离
          dist=dot(a(j,:),data(j,:))/(norm(a(j,:))*norm(data(j,:)));%余弦距离
          dist=1/(2+dist);%余弦距离
        affinity(i,j)= exp(-dist/(2*sigma^2)); 
    end
end
end

function sigma=getSigma(data)
%依据样本数据的标准差作为缩放参数
sigma=0;
for i=1:size(data,2)
    sigma=sigma+std(data(:,i))^2;
end
sigma=sqrt(sigma);
end