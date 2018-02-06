# Lucene-search-and-indexing
CS980.01 Adv Top/ Data Science with Knowledge Graphs, Assignment1:

# Installation Instructions 
### Download as a .zip file or clone repository:
```
git clone https://github.com/AjeshV/Lucene-search-and-indexing.git
```

###  Input files are at:

```
http://trec-car.cs.unh.edu/datareleases/
```

###  From pom.xml directory, type:
```
mvn package
```

###  Type the below command to execute code:
```
java -jar searchandIndexing-0.0.1-SNAPSHOT.jar <inputfiles>
```

###  Input file format for Indexing and search: 

```
<Paragraphscborfile> <Pathtoluceneindex>
<Outlinescborfile> <IndexPath> <Pagesoutput> <Sectionsoutput>
```

From IDE (ex: Eclipse) code can be run by providing input files as arguments.
