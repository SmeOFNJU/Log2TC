# #create test script
# import re
# def createTestCase(proName):
#     result=[]
#     testNum=0
#     with open(r'/home/zzx/Desktop/siemens suites/printtokens/scripts/runall.sh','r') as f:
#         for line in f.readlines():
#             result.append(line)
#             m = re.match(r'echo .*test (.*)".*', line)
#             if m:
#                 testNum=m.group(1)
#             if re.match(r'[.][.][/].*',line):
#                 result.append('cd ../source\ngcov -a -b print_tokens.c\ncp print_tokens.c.gcov ../test_gcov/test%s.txt\nrm print_tokens.gcda\ncd ../scripts\n'%(testNum))
#     with open(r'/home/zzx/Desktop/siemens suites/printtokens/scripts/newrunall.sh','w') as f:
#         for line in result:
#             f.write(line)
#
#
# if __name__=="__main__":
#     createTestCase()
#create test script
# import re
# def createTestCase(proName):
#     result=[]
#     testNum=0
#     with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/runall.sh'%(proName),'r') as f:
#         for line in f.readlines():
#             result.append(line)
#             m = re.match(r'echo .*test (.*)".*', line)
#             if m:
#                 testNum=m.group(1)
#             if re.match(r'[.][.][/].*',line):
#                 result.append('cd ../source\ngcov -a -b %s.c\ncp %s.c.gcov ../test_gcov/test%s.txt\nrm %s.gcda\ncd ../scripts\n'%(proName,proName,testNum,proName))
#     with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/newrunall.sh'%(proName),'w') as f:
#         for line in result:
#             f.write(line)
#
#
# if __name__=="__main__":
#     createTestCase("printtokens2")
#     createTestCase("schedule")
#     createTestCase("schedule2")
# create test script
import re


def createTestCase(proName):
    result = []
    testNum = 0
    with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/runall.sh' % (proName), 'r') as f:
        for line in f.readlines():
            result.append(line)
            m = re.match(r'echo .*test (.*)".*', line)
            if m:
                testNum = m.group(1)
            if re.match(r'[.][.][/].*', line):
                result.append(
                    'cd ../source\ngcov -a -b %s.c\ncp %s.c.gcov ../test_gcov/test%s.txt\nrm %s.gcda\ncd ../scripts\n' % (
                    proName, proName, testNum, proName))
    with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/newrunall.sh' % (proName), 'w') as f:
        for line in result:
            f.write(line)


def createTestCase2(proName, otherName):
    result = []
    testNum = 0
    with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/runall.sh' % (proName), 'r') as f:
        for line in f.readlines():
            result.append(line)
            m = re.match(r'echo .*test (.*)".*', line)
            if m:
                testNum = m.group(1)
            if re.match(r'[.][.][/].*', line):
                result.append(
                    'cd ../source\ngcov -a -b %s.c\ncp %s.c.gcov ../test_gcov/test%s.txt\nrm %s.gcda\ncd ../scripts\n' % (
                    otherName, otherName, testNum, otherName))
    with open(r'/home/zzx/Desktop/siemens suites/%s/scripts/newrunall.sh' % (proName), 'w') as f:
        for line in result:
            f.write(line)


if __name__ == "__main__":
    createTestCase2("printtokens2", "print_tokens2")
    createTestCase("schedule")
    createTestCase("schedule2")