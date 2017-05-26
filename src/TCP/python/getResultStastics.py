import re

def getAvg(infile):
	suiteNum=500
	sumAPFD=0
	with open(infile,'r') as inf:
		for line in inf.readlines():
			m=re.match(r'bestAPFD:(.*)',line)
			if m:
				sumAPFD=sumAPFD+float(m.group(1))
	print sumAPFD/suiteNum,'\n'
	
	
def getBoxPlotData(infileGSO,infileGA,infileAG,outFile,prog):
	dataGSO=[]
	dataGA=[]
	dataAG=[]
	with open(infileGSO,'r') as inf:
		for line in inf.readlines():
			m=re.match(r'bestAPFD:(.*)',line)
			if m:
				dataGSO.append(float(m.group(1)))
	with open(infileGA,'r') as diffInF:
		for line in diffInF.readlines():
			m=re.match(r'bestAPFD:(.*)',line)
			if m:
				dataGA.append(float(m.group(1)))			
	with open(infileAG,'r') as agInF:
		for line in agInF.readlines():
			m=re.match(r'bestAPFD:(.*)',line)
			if m:
				dataAG.append(float(m.group(1)))
	#dataGSO & dataGA &dataAG has the same length			
	with open(outFile,'w') as outf:
		for i in range(len(dataGSO)):
			outf.write('GSO,%f,%s\n'%(dataGSO[i],prog))
		for i in range(len(dataGA)):
			outf.write('GA,%f,%s\n'%(dataGA[i],prog))
		for i in range(len(dataAG)):
			outf.write('AGreedy,%f,%s\n'%(dataAG[i],prog))
			
	
if __name__ == '__main__':
	# print 'state:'
	# getAvg('../covInfo/schedule22/statePSOResult.txt')
	# print 'block:'
	# getAvg('../covInfo/schedule22/blockPSOResult.txt')
	# print 'branch:'
	# getAvg('../covInfo/schedule22/branchPSOResult.txt')
	getBoxPlotData('../covInfo/schedule2/stateGSOResult.txt','../covInfo/schedule2/stateGAResult.txt','../AGreedy/schedule2/stateAGResult.txt','../covInfo/schedule2/stateCompData2.txt','schedule2_state')
	getBoxPlotData('../covInfo/schedule2/blockGSOResult.txt','../covInfo/schedule2/blockGAResult.txt','../AGreedy/schedule2/blockAGResult.txt','../covInfo/schedule2/blockCompData2.txt','schedule2_block')
	getBoxPlotData('../covInfo/schedule2/branchGSOResult.txt','../covInfo/schedule2/branchGAResult.txt','../AGreedy/schedule2/branchAGResult.txt','../covInfo/schedule2/branchCompData2.txt','schedule2_branch')
	#getBoxPlotData('../covInfo/schedule22/branchGSOResult.txt','../covInfo/schedule22/branchGAResult.txt','../AGreedy/schedule22/branchAGResult.txt','../covInfo/schedule22/branchCompData.txt')
	
	
	