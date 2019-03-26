# Pulsar Coding Challenge Solutions

This are the solutions to the technichal test for data engineering candidates for the Pulsar Team at Dow Jones.

## HouseOfPulsar

I write a solution to the data challenge using Scio.

### To run the job with sbt

```
sbt

runMain Wizeline.InvertedIndex --input=[Path to the datasets folder] --output=[Output folder path]
```

example:

```
runMain Wizeline.InvertedIndex --input=/Users/victor.garcia/Documents/pulsar/pulsar-coding-challenge/datasets/* --output=/Users/victor.garcia/Documents/pulsar/houseofpulsar/output
```

### Sample output

Dictionnary file (word, unique UUID) :
```
(pondering,8ade7dd4-a2d2-40d4-926b-16113d3001e8)
(significant,f1122c83-447c-4ab1-be0c-a2d0031d5645)
(worse,134c29cd-a535-4731-9ffa-6556dde19a3f)
(lifted,cdb6a8bb-fbae-48eb-8af6-d026d7e7456e)
(telephone,1a2257cc-fc50-4896-b9f8-7883e4307529)
(viatka,d22ce92d-3765-47f4-a029-30478d58e5cd)
(delicious,f750e3a3-11fb-4439-a625-20647aa74957)
```

Inverted Index file (unique UUID, Ordered List of doc's where that word is in) :
Due to Scala I didn't fund a why to print the list only with [ ] instead of List(...)

```
(8ee6bd2a-89f8-4446-a4c7-2ff8a745f71a,List(4, 40, 41, 42, 43, 44))
(e314fc98-2e25-49fa-86ab-df3448a9e3ca,List(41))
(1cd8acc4-c31a-424c-a81e-1e5fefd3031a,List(4, 42, 43, 44))
(92b75fa7-ac59-4564-ad41-9e287b3fb844,List(40, 41, 43, 44))
(aa40a0be-6bcf-4d62-a329-9fc9a55876b4,List(4, 41, 43))
(4d3986ab-7664-4197-8c0a-a6793145dab1,List(4))
(cd31f6c7-04a7-456b-87e3-d8db856e52ca,List(4, 40, 41, 43, 44))
(2563a3c5-9137-430a-8912-7d6018a480a8,List(41, 43))
(1b134096-021d-4ac6-ad02-16b339fec10c,List(4, 41, 43))
(52da9a5e-00a5-4e43-99cd-8f0a138fc342,List(43))
(43bcb198-5200-44f1-80e3-aba1a8a365c3,List(4, 41, 43, 44))
(2e0e163d-70cc-4c05-a4fd-d1803d102c79,List(4))
(ac960cc2-6aa9-4503-99cb-0a6dc8eb9d02,List(41))
```

This project is based on the [scio.g8](https://github.com/spotify/scio.g8).
