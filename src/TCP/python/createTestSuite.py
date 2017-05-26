#create test suite from test pool with branch coverage up to 100%
import re
import random
#print_tokens
#setBranch=set([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 63, 64, 65, 66, 67, 68, 69, 70, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109])
#print_tokens2
setBranch=set([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 38, 39, 40, 41, 42, 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162])
#schedule
#setBranch=set([1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66])
#schedule2
#setBranch=set([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88])
def createTestSuite(proName):
    # testSuite = []
    # suiteStateCovInfo = []
    # suiteBlockCovInfo = []
    # suiteBranchCovInfo = []
    # testStateCovInfo = set()
    # testBlockCovInfo = set()
    # testBranchCovInfo = set()
    # allBranchCovInfo = set()

    #get covfile info
    covFileInfo=[]
    with open(r'/home/zzx/Desktop/siemens suites/%s/info/overallCovInfo.txt'%(proName),'r') as covInfoF:
        for line in covInfoF.readlines():
            covFileInfo.append(line.strip())
    stateFile=open(r'/home/zzx/Desktop/siemens suites/%s/info/stateFile.txt'%(proName),'w')
    blockFile=open(r'/home/zzx/Desktop/siemens suites/%s/info/blockFile.txt'%(proName),'w')
    branchFile=open(r'/home/zzx/Desktop/siemens suites/%s/info/branchFile.txt'%(proName),'w')
    # random select a test case from test p
    for i in range(500):
        testSuite = []
        suiteStateCovInfo = []
        suiteBlockCovInfo = []
        suiteBranchCovInfo = []
        allBranchCovInfo = set()
        while(len(allBranchCovInfo) < len(setBranch)):
            ranNum=random.randint(1,4130)
            lineNum=0;
            for line in covFileInfo:
                lineNum=lineNum+1
                m=re.match(r'test(\d+)\s+coverage\s+info.*',line)
                if m and ranNum == int(m.group(1)):
                    break
            s=covFileInfo[lineNum-1+6].split(',')
            s.pop()
            tmpBranchSet=allBranchCovInfo.copy()
            for c in s:
                allBranchCovInfo.add(int(c))
            if(len(allBranchCovInfo) > len(tmpBranchSet)):
                testSuite.append(ranNum)
                suiteStateCovInfo.append(covFileInfo[lineNum-1+2])
                suiteBlockCovInfo.append(covFileInfo[lineNum-1+4])
                suiteBranchCovInfo.append(covFileInfo[lineNum-1+6])
        stateFile.write('test suite%d\ntest case:'%(i+1))
        for num in testSuite:
            stateFile.write('%d,'%(num))
        stateFile.write('\n')
        for cov in suiteStateCovInfo:
            stateFile.write('%s|'%(cov))
        stateFile.write('\n')

        blockFile.write('test suite%d\ntest case:' % (i + 1))
        for num in testSuite:
            blockFile.write('%d,' % (num))
        blockFile.write('\n')
        for cov in suiteBlockCovInfo:
            blockFile.write('%s|' % (cov))
        blockFile.write('\n')

        branchFile.write('test suite%d\ntest case:' % (i + 1))
        for num in testSuite:
            branchFile.write('%d,' % (num))
        branchFile.write('\n')
        for cov in suiteBranchCovInfo:
            branchFile.write('%s|' % (cov))
        branchFile.write('\n')

    stateFile.close()
    blockFile.close()
    branchFile.close()

def getAvgSuiteSize(proName):
    suiteSize=[]
    with open(r'/home/zzx/Desktop/siemens suites/%s/info/stateFile.txt'%(proName),'r') as inFileF:
        for line in inFileF.readlines():
            m=re.match(r'test\s+case:(.*),\n',line)
            if m:
                suiteSize.append(len(m.group(1).split(',')))
    sum=0
    for size in suiteSize:
        sum=sum+size
    print sum/len(suiteSize)


if __name__=='__main__':
    #createTestSuite("printtokens")
    getAvgSuiteSize("printtokens")
	createTestSuite("printtokens2")
    getAvgSuiteSize("printtokens2")
	# createTestSuite("schedule")
    # getAvgSuiteSize("schedule")
	# createTestSuite("schedule2")
    # getAvgSuiteSize("schedule2")



