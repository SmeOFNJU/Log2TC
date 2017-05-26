%获取TCP结果
%分别使用GSO、GA、AGreedy算法
function getTCPResult(infile,outfile)
    fin=fopen(infile,'r');
    testSuite=[];
    testOriSuite=[];
    testCov={};
    resultAPFD=[];
    resultSuite={};
    count=1;
    while feof(fin)==0
        str=fgetl(fin);
        %获取测试用例
        if ~isempty(regexp(str,'suite','match'))
            testSuite=[];
            testOriSuite=[];
            testCov={};
        end
        s=regexp(str,':','split');
        if ~isempty(s) && length(s)>1
            m=regexp(s(2),',','split');
            if ~isempty(m)
                m=m{1,1};
                for i=1:1:(length(m)-1)
                    testOriSuite(i)=str2double(m(i));
                    testSuite(i)=i;
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
        
        if length(testCov)>0 && length(testSuite)>0
            bestAPFD=0;
            bestSuite=[];
            [bestAPFD,bestSuite]=GSO(testSuite,testCov);
            newBestSuite=[];
            for q=1:1:length(testOriSuite)
                newBestSuite(q)=testOriSuite(bestSuite(q));
            end
            resultAPFD(count)=bestAPFD;
            resultSuite{count}=newBestSuite;
            count=count+1;
        end    
    end
     fclose(fin); 
     
     %保存结果到文件
     fout=fopen(outfile,'w');
     for p=1:1:count-1
         fprintf(fout,'test suite%d\n',p);
         fprintf(fout,'bestAPFD:%f\n',resultAPFD(p));
         fprintf(fout,'bestSuite:');
         for location=1:1:length(resultSuite{p})
             fprintf(fout,'%d',resultSuite{p}(location));
             if location < length(resultSuite{p})
                 fprintf(fout,',');
             end
         end
         fprintf(fout,'\n\n');
     end
     fclose(fout);
     
     
end