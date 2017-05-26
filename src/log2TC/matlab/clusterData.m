function [data,frequency]=clusterData()
attr=getAttr();
dataFile=fopen('clusterData.txt','r');
data=[];
while feof(dataFile)==0
    str=fgetl(dataFile);
    s=regexp(str,' ','split');
    tmpRow=zeros(1,length(attr));
    if ~isempty(s)
        for i=1:length(s)
            x=str2double(cell2mat(s(i)));
            location=find(attr==x);
            if ~isempty(location)
                tmpRow(location)=tmpRow(location)+1;
            end
        end
    end
    data=[data;tmpRow];
end
frequency=[];
n=size(data,2);
for i=1:n
    frequency=[frequency sum(data(:,i))];
end
frequency=[attr;frequency];
frequency=frequency';
end

function attr=getAttr()
dataFile=fopen('clusterData.txt','r');
attr=[];
while feof(dataFile) == 0
    str=fgetl(dataFile);
    s=regexp(str,' ','split');
    if ~isempty(s)
        for i=1:length(s)
            x=str2double(cell2mat(s(i)));
            if isempty(find(attr==x))
                attr=[attr x];
            end
        end
    end    
end
fclose(dataFile);
end