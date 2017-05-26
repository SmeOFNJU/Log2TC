# from matplotlib import pylab
def mergeTwoFile(otFile,oriFile,outFile):
    with open(outFile,'w') as resultFile:
        with open(otFile,'r') as ot:
            for line in ot.readlines():
                resultFile.write(line)
        with open(oriFile,'r') as ori:
            for line in ori.readlines():
                resultFile.write(line)
def drawPic(otFile,oriFile):
    otList=[]
    oriList=[]
    with open(otFile,'r') as ot:
        for line in ot.readlines():
            otList.append(line)
    with open(oriFile,'r') as ori:
        for line in ori.readlines():
            oriList.append(line)
    otList.plot()
    oriList.plot()
if __name__=='__main__':
    # mergeTwoFile('../covInfo/print_tokens2/stateOT.txt','../covInfo/print_tokens2/stateOri.txt','../covInfo/print_tokens2/stateAllOT.txt')
    # mergeTwoFile('../covInfo/print_tokens2/blockOT.txt', '../covInfo/print_tokens2/blockOri.txt','../covInfo/print_tokens2/blockAllOT.txt')
    # mergeTwoFile('../covInfo/print_tokens2/branchOT.txt', '../covInfo/print_tokens2/branchOri.txt','../covInfo/print_tokens2/branchAllOT.txt')
    # mergeTwoFile('../covInfo/print_tokens/stateOT.txt','../covInfo/print_tokens/stateOri.txt','../covInfo/print_tokens/stateAllOT.txt')
    # mergeTwoFile('../covInfo/print_tokens/blockOT.txt', '../covInfo/print_tokens/blockOri.txt','../covInfo/print_tokens/blockAllOT.txt')
    # mergeTwoFile('../covInfo/print_tokens/branchOT.txt', '../covInfo/print_tokens/branchOri.txt','../covInfo/print_tokens/branchAllOT.txt')
    # mergeTwoFile('../covInfo/schedule/stateOT.txt', '../covInfo/schedule/stateOri.txt', '../covInfo/schedule/stateAllOT.txt')
    # mergeTwoFile('../covInfo/schedule/blockOT.txt', '../covInfo/schedule/blockOri.txt','../covInfo/schedule/blockAllOT.txt')
    # mergeTwoFile('../covInfo/schedule/branchOT.txt', '../covInfo/schedule/branchOri.txt','../covInfo/schedule/branchAllOT.txt')
    # mergeTwoFile('../covInfo/schedule2/stateOT.txt', '../covInfo/schedule2/stateOri.txt', '../covInfo/schedule2/stateAllOT.txt')
    # mergeTwoFile('../covInfo/schedule2/blockOT.txt', '../covInfo/schedule2/blockOri.txt','../covInfo/schedule2/blockAllOT.txt')
    # mergeTwoFile('../covInfo/schedule2/branchOT.txt', '../covInfo/schedule2/branchOri.txt','../covInfo/schedule2/branchAllOT.txt')
    drawPic('../covInfo/schedule2/branchOT.txt', '../covInfo/schedule2/branchOri.txt')