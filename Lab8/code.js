var waitingForFork = 0;
var waitingForConductor = 0;
 
var Fork = function() {
    this.state = 0;
    return this;
}
 
Fork.prototype.acquire = function(cb) {
    var tryAfter = timeout => () => {
        if (this.state == 0) {
            this.state = 1;
            if (cb) cb();
        }
        else {
            waitingForFork += timeout * 2;
            //console.log(timeout*2)
            setTimeout(tryAfter(timeout * 2), timeout * 2);
        }
    };
    waitingForFork += 1;
    setTimeout(tryAfter(1), 1);
}
 
Fork.prototype.release = function() { 
    this.state = 0; 
}
 
var Conductor = function(initial_state) {
    this.state = initial_state;
    return this;
}
 
Conductor.prototype.acquire = function(cb) {
    var tryAfter = timeout => () => {
        if (this.state > 0) {
            this.state -= 1;
            if (cb) cb();
        }
        else {
            waitingForConductor += timeout * 2;
            setTimeout(tryAfter(timeout * 2), timeout * 2);
        }
    };
 
    waitingForConductor += 1;
    setTimeout(tryAfter(1), 1);
}
 
Conductor.prototype.release = function(cb) {
    this.state += 1;
}
 
var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}
 
Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
 
    var runs = remainingRuns => {
        forks[f1].acquire(() => {
            forks[f2].acquire(() => {
                console.log(`Philosopher ${this.id} is eating something`);
                setTimeout(() => {
                    forks[f2].release();
                    forks[f1].release();
                    if (remainingRuns > 0) {
                        runs(remainingRuns - 1);
                    }
                })
            })
        })
    };
 
    runs(count);
}
 
Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
 
    var runs = remainingRuns => {
        forks[id % 2 == 0 ? f1 : f2].acquire(() => {
            forks[id % 2 == 0 ? f2 : f1].acquire(() => {
                console.log(`Philosopher ${this.id} is eating something`);
                setTimeout(() => {
                    forks[f2].release();
                    forks[f1].release();
                    if (remainingRuns > 0) {
                        runs(remainingRuns - 1);
                    }
                    else {
                        console.log(`Total achieved time: ${waitingForFork} micros`)
                    }
                })
            })
        })
    };
 
    runs(count);
}
 
Philosopher.prototype.startConductor = function(count, conductor) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    var runs = remainingRuns => {
        conductor.acquire(() => {
            forks[f1].acquire(() => {
                forks[f2].acquire(() => {
                    console.log(`Philosopher ${this.id} is eating something`);
                    setTimeout(() => {
                        forks[f2].release();
                        forks[f1].release();
                        conductor.release();
 
                        if (remainingRuns > 0) {
                            runs(remainingRuns - 1);
                        }
                        else {
                            console.log(`Total achieved time: ${waitingForConductor} micros`)
                        }
                    })
                })
            })
        })
    };
    runs(count);
}


for (var N = 10; N<=10; N+=10){
  console.log(`----------------------TIME FOR N = ${N}-----------------------------------`)
  var forks = [];
  var philosophers = []

  for (var i = 0; i < N; i++) {
    forks.push(new Fork());
  }

  for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
  }
  var conductor = new Conductor(N - 1);
  for (var i = 0; i < N; i++) {
  philosophers[i].startNaive(10);
    //philosophers[i].startAsym(10);
    //philosophers[i].startConductor(10, conductor);
  }
}

 