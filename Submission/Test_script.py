from scipy.spatial import distance
from scipy.spatial.distance import cosine
from heapq import nsmallest
import pandas as pd
import numpy as np

def second_smallest(n): 
    return nsmallest(2, n)[-1]
def third_smallest(n):
    return nsmallest(3, n)[-1]
    
tcount=0 
fcount=0
df=pd.read_csv('D:\SEM-3\SIMFIC_Project\Features1.csv')
for i in range(15): #looping over books
    s=[]
    for j in range(15):
        #print(distance.euclidean(df.iloc[i][:19], df.iloc[j][:19]))
        if(cosine(df.iloc[i][:19], df.iloc[j][:19])==0):
            a=100000
        else:
            a=cosine(df.iloc[i][:19], df.iloc[j][:19]) # cosine similarity of book vectors
        s.append(a)
    #print(np.argmin(s))
    tstr=df.iloc[i]['books']
    #print(tstr)

    if 'Doyle' in tstr:
        if ('Doyle' in df.iloc[np.argmin(s)]['books']) or ('Doyle' in df.iloc[s.index(second_smallest(s))]['books'])or ('Doyle' in df.iloc[s.index(third_smallest(s))]['books']):#checks if similar books are in top 3 results
                #print ("true")
                tcount+=1

        else:
            #print('false')
            fcount+=1
    elif 'Dickens' in tstr:
        if ('Dickens' in df.iloc[np.argmin(s)]['books'])  or ('Dickens' in df.iloc[s.index(second_smallest(s))]['books'])or ('Dickens' in df.iloc[s.index(third_smallest(s))]['books']):
                #print ("true")
                tcount+=1


        else:
            #print('false')
            fcount+=1
    elif 'Jane' in tstr:
        if ('Jane' in df.iloc[np.argmin(s)]['books']) or ('Jane' in df.iloc[s.index(second_smallest(s))]['books'])or ('Jane' in df.iloc[s.index(third_smallest(s))]['books']):
                #print ("true")
                tcount+=1
       

        else:
            #print('false')
            fcount+=1
print(tcount,fcount)