#-*- coding:utf-8 -*-
def modifyFucid():
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\funcid.txt'
    unipath = unicode(inpath, 'utf8')
    funcList=[]
    with open(unipath, 'r') as funcFile:
        for line in funcFile.readlines():
            s = line.strip()
            s="6"+s
            funcList.append(s)
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\Finalfuncid.txt'
    unipath = unicode(inpath, 'utf8')
    with open(unipath, 'w') as funcFile:
        for line in funcList:
            funcFile.write(line);
            funcFile.write("\n");

if __name__=="__main__":
    modifyFucid()