# Loopfreight
 
To Solve the problem  i had planned 3 approch 
-> using Future 
-> using Await & Async 
-> combine use of observer and coroutine making all call background thread  

Form the above approch i picked the last one as Future in kotlin do work at backgorund thread so, i decided to end up using coroutine with observer pattern. Decided to not implement a full MVVM pattern (had a short time sorry). To Achieve that i had to remove the observer after every iteration to prevent data duplication. Finally Endless Scroll was implemented by listening to the RCV scroll and counting the item diplayed by the layout manager. Then requesting to the api with new page number. 

# Solving Approch
Intially  i get the search result and loop the reuslt and make new request for repo stat as github api has a quota for request so it returns empty object rather than empty array 

# Screen Shot 


| <img src="../main/ss/1.png" width="250"> | <img src="../main/ss/2.png" width="250"> |

# Project Demo 
<img src="https://media.giphy.com/media/XP9SUQARpsGqKIkiFI/giphy.gif" width="222" height="480" />
