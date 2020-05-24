import pandas as pd
import requests
from bs4 import BeautifulSoup
#from nltk.probability import FreqDist
import nltk
from nltk.tokenize import sent_tokenize, word_tokenize
from gensim.models import Word2Vec
import numpy as np
from sklearn.decomposition import PCA
#import glob
from os import listdir
from os.path import isfile, join
import re
import itertools
import matplotlib.pyplot as plt
import math
from nltk import tokenize
#pd.options.display.float_format = '{:.2f}'.format

def Book_Chunker(books):
    m=""
    book_chunks=[]
    for i in range(len(books)):
        soup = BeautifulSoup(open(books[i]), 'html5lib')
        x1=soup.find_all('p')
        for j in x1:
            m=m+j.text #string containing the whole book
        dict = {}
        no_parts = 10 #no.of chunks per book
        length = math.floor(len(m)/no_parts)
        sentences = tokenize.sent_tokenize(m) #sentence tokenizing text to avoid breaking the sentences during chunking.

        flag = 0
        c = 0

        for k in range(no_parts):
          content = ""
          flag = 0
          while flag == 0:
            dict["part"+str(k)] = content #dictionary containing approximately equal chunks 
            content = content + sentences[c]
            c = c + 1
            if (len(content) > length) or (c >= len(sentences)):    
              flag = 1
              c = c-2
        book_chunks.append([ v for v in dict.values() ]) # list containing chunks of all books
    return book_chunks

   
def Book_Vectors(book_chunks): 
    book_vectors=[]
    for i in range(len(book_chunks)):
            chunk_vectors=[] 
            for j in range(len(book_chunks[i])):            
                result = [word_tokenize(t) for t in sent_tokenize(book_chunks[i][j])]
                results=[[w.lower() for w in x if w.isalpha()] for x in result] #sentence tokenized input for Word2vec without punctuation 
                model = Word2Vec(results,size=100,sg=1,window=10) #Word2vec Skipgram model trained individually per chunk                
                v=[]

                words = list(model.wv.vocab) #vocabulary of the model
                for word in words:
                    #v.append(model[word])
                    v.append((model.syn1neg[model.wv.vocab[word].index]+model[word])/2) #average of context and center wordvectors for each word 
                chunk_vectors.append(sum(v)/len(v)) #average of all word vectors per chunk
            book_vectors.append(chunk_vectors) # list containing chunk vectors of all books
    return book_vectors

def Vectors_Transform(book_vectors):
    
    pca = PCA(n_components=2) #each chunk vector is reduced to 2 dimension
    final_vectors=[]
    for i in range(len(book_vectors)):
        X1 = pca.fit_transform(book_vectors[i]) # reducing the dimensions of all vectors using PCA
        final_vectors.append([item for sublist in X1 for item in sublist]) #list of all transformed vectors for all books

    df = pd.DataFrame()   
    df=df.append(pd.DataFrame(final_vectors)) #each row of df represents genre feature of a book
    b=[(i.split('\\')[-1]).split('-')[0]  for i in books]
    df.insert(loc=0, column='Book_Id', value=b)
    #df.to_csv('D:\SEM-3\SIMFIC_Project\Features.csv',index=False) #write to CSV
    df.to_csv('D:\SEM-3\SIMFIC_Project\Features1.csv', index=False,float_format='%11.6f')



path=r'D:\SEM-3\Old_dump' #path containing htmls files of books
books = [join(path, f) for f in listdir(path) ]
a=Book_Vectors(Book_Chunker(books))
Vectors_Transform(a)
