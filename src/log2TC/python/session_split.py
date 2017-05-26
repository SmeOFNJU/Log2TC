#-*- coding:utf-8 -*-
import re

sessionList = []
sessionDict = {}
def splitSession():

    session=[]
    userId=''
    curTime=0
    inpath=r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217+user+time+func.txt'
    unipath=unicode(inpath,'utf8')
    with open(unipath,'r') as logFile:
        for line in logFile.readlines():
            m=re.match(r'^(\w+)\s+(\w+)\s+(\w+)\s+(\w+)-(\w+)\s+(\d+)\s+$',line)
            if m:
                tmpTime=int(m.group(5))
                funcid=int(m.group(6))
                if userId == m.group(1)+m.group(2)+m.group(3) and tmpTime/10000*3600+tmpTime%10000/100*60+tmpTime%100 -(curTime/10000*3600+curTime%10000/100*60+curTime%100) < 28*60:
                    session.append(funcid)
                else:
                    if len(session) > 0:
                        sessionList.append(session)
                    session=[]
                    session.append(funcid)
                userId=m.group(1)+m.group(2)+m.group(3)
                curTime=tmpTime
    print len(sessionList)

def sessionCount():
    for s in sessionList:
        t=tuple(s)
        if t in sessionDict:
            sessionDict[t] += 1
        else:
            sessionDict[t] = 1
    print len(sessionDict)
    # inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217session.txt'
    # unipath = unicode(inpath, 'utf8')
    # dict = sorted(sessionDict.iteritems(), key=lambda d: d[1], reverse=True)
    # with open(unipath,'w') as sessionFile:
    #     for ss,count in dict:
    #         flowStr=''
    #         for func in ss:
    #             flowStr=flowStr+str(func)+' '
    #         flowStr=flowStr.rstrip()
    #         sessionFile.write('%s,%d\n'%(flowStr,count))
def findConFunc():
    funcDict={}
    for s in sessionDict.keys():
        formerFuc=0
        for func in s:
            if formerFuc == func:
                if func in funcDict:
                    funcDict[func]=max(funcDict[func],sessionDict[s])
                else:
                    funcDict[func]=sessionDict[s]
            formerFuc=func
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217DuplicateFunc.txt'
    unipath = unicode(inpath, 'utf8')
    with open(unipath,'w') as dupFucFile:
        for func in funcDict.keys():
            dupFucFile.write('%d,%d\n'%(func,funcDict[func]))

def mergeFunc():
    newsessionDict = {}
    for s in sessionDict.keys():
        formerFuc = 0
        funcList=[]
        for func in s:
            if formerFuc != func:
                funcList.append(func)
            formerFuc = func
        if tuple(funcList) in newsessionDict:
            newsessionDict[tuple(funcList)]=sessionDict[s]+newsessionDict[tuple(funcList)]
        else:
            newsessionDict[tuple(funcList)]=sessionDict[s]
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217newSession.txt'
    unipath = unicode(inpath, 'utf8')
    dict = sorted(newsessionDict.iteritems(), key=lambda d: d[1], reverse=True)
    with open(unipath,'w') as sessionFile:
        for ss,count in dict:
            flowStr=''
            for func in ss:
                flowStr=flowStr+str(func)+' '
            flowStr=flowStr.rstrip()
            sessionFile.write('%s,%d\n'%(flowStr,count))

def genUpdatedFile():
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217newUpdatedSession.txt'
    unipath = unicode(inpath, 'utf8')
    inpath1 = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217newSession.txt'
    unipath1 = unicode(inpath1, 'utf8')
    with open(unipath,'w') as upFile:
        with open(unipath1,'r') as readFile:
            for line in readFile.readlines():
                cur=line.strip().split(',')
                if len(cur[0].split()) > 1:
                    upFile.write('%s'%line)

def genCsvFile():
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217newUpdatedSession.txt'
    unipath = unicode(inpath, 'utf8')
    csvpath=r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\20170217SessionCsv.csv'
    uni_csvpath=unicode(csvpath, 'utf8')
    with open(uni_csvpath,'w') as csvFile:
        with open(unipath,'r') as upFile:
            csvFile.write('caseID;task\n')
            caseCount=0
            for line in upFile.readlines():
                if caseCount <=222:
                    cur=line.split(',')
                    eventList=cur[0].split()
                    for e in eventList:
                        csvFile.write('%d;%s\n'%(caseCount,e))
                    caseCount+=1


if __name__=='__main__':
    # splitSession()
    # sessionCount()
    # findConFunc()
    # mergeFunc()
    # genUpdatedFile()
    genCsvFile()
