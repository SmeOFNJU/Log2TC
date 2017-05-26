import re

myDic={}
def getMyDic(proName):
	 with open(r'D:/space_2.0/%s/test_gcov/test1.txt'%(proName),'r') as inf:
		blockCount=0
		for line in inf.readlines():
			m=re.match(r'.*[:]\s+(\d+)-block\s+(\d+).*',line)
			if m:
				blockCount=blockCount+1
				myDic['%s:%s'%(m.group(1),m.group(2))]=blockCount

def modifyFile(proName):
	lineCount=0
	with open(r'D:/space_2.0/%s/info/blockFile.txt'%(proName),'r') as inf:
		with open(r'D:/space_2.0/%s/info/newBlockFile.txt'%(proName),'w') as outf:
			for line in inf.readlines():
				lineCount=lineCount+1
				if lineCount%3==0:
					for key in myDic:
						line=line.replace(key,'%s'%(myDic[key]))
				outf.write(line)
	
if __name__=='__main__':
	getMyDic('space')
	modifyFile('space')
	print myDic

