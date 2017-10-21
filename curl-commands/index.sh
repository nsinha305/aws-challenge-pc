curl -XPUT https://search-nsinha-pc-6x4ctzvktckl5u3rbktxjduoj4.us-east-2.es.amazonaws.com/form -d '
{                
"settings" : {
"index" : {
"number_of_shards" : 5,
"number_of_replicas" : 0
}
}
}
'

