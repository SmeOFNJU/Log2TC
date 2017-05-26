#-*- coding:utf-8 -*-
import re
from Levenshtein import *
clusterNum=5
tcList = []
clusterList = []
tcMapping={}
def getClusterRe():
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\clusterData_TC.txt'
    unipath = unicode(inpath, 'utf8')
    with open(unipath, 'r') as tcFile:
        for line in tcFile.readlines():
            s=line.strip().split(' ')
            tcList.append(s)
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\clusterData_cluster.txt'
    unipath = unicode(inpath, 'utf8')
    with open(unipath, 'r') as clusterFile:
        for line in clusterFile.readlines():
            s=line.strip()
            clusterList.append(s)
    del tcList[0]
    del clusterList[0]

def getTcMapping():
    tcSet=set([])
    for ts in tcList:
        for tc in ts:
            tcSet.add(tc)
    i=0
    for tc in tcSet:
        tcMapping[tc]=chr(ord('A')+i)
        i=i+1
    print tcMapping

def getTestSuite():
    tcNum=len(tcList)
    tsDict={}
    finalDict={}
    for i in range(1,clusterNum+1):
        tmpTCList=[]
        for j in range(0,tcNum):
            if int(clusterList[j]) == i:
                tmpTCList.append(tcList[j])
        tsDict[i]=tmpTCList
    for key in tsDict.keys():
        tcSet=set([])
        finalLsit=[]
        for ts in tsDict[key]:
            for tc in ts:
                tcSet.add(tc)
        tmpSet=set([])
        while len(tmpSet)<len(tcSet):
            max=0
            tmpTS=[];
            for ts in tsDict[key]:
                if len(set(ts)&(tcSet-tmpSet))>max:
                    max=len(set(ts))
                    tmpTS=ts
                elif len(set(ts)&(tcSet-tmpSet))==max:
                    if len(ts) > len(tmpTS):
                        tmpTS=ts
            for tmpTc in tmpTS:
                tmpSet.add(tmpTc)
            finalLsit.append(tmpTS)
        #calculate Edit dist
        maxDist=0
        strMax=''
        rever_TS=[]
        for tc in finalLsit[0]:
            strMax=strMax+tcMapping[tc]
        for ts in tsDict[key]:
            strMin=''
            for tc in ts:
                strMin=strMin+tcMapping[tc]
            if distance(strMin,strMax)>maxDist:
                rever_TS=ts
                maxDist=distance(strMin,strMax)
        flag=True
        for list in finalLsit:
            if rever_TS == list:
                flag=False
        if flag:
            finalLsit.append(rever_TS)
        finalDict[key]=finalLsit
    print finalDict
    #write to file
    inpath = r'C:\Users\zzxWin7\Desktop\日志分析论文\日志数据\clusterData_Result.txt'
    unipath = unicode(inpath, 'utf8')
    with open(unipath,'w') as reFile:
        for key in finalDict.keys():
            for ts in finalDict[key]:
                reFile.write('%d:'%key)
                line=''
                for tc in ts:
                    line=line+tc+' '
                line.rstrip()
                reFile.write('%s\n'%line)
        for key in tcMapping.keys():
            reFile.write('%s:%s\n'%(key,tcMapping[key]))






if __name__=='__main__':
    getClusterRe()
    getTcMapping()
    getTestSuite()