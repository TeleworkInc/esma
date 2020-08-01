#include <ctype.h>
#include <stdio.h>
#include <iostream>

enum State { BEFORE, INSIDE, AFTER };

struct Branch {
    enum State const next_state;
    void (*action)(int);
};

class StateMachine {
   public:
    StateMachine();
    void feedChar(int);

   protected:
    static void nop(int);
    static void print(int);

   private:
    enum State _state;
    static struct Branch const _transitions[3][3];
};

StateMachine::StateMachine() : _state(BEFORE) {}

void StateMachine::feedChar(int const c) {
    int const row = (_state == BEFORE) ? 0 : (_state == INSIDE) ? 1 : 2;
    int const column = (c == '\n') ? 0 : isspace(c) ? 1 : 2;
    struct Branch const *const b = &_transitions[row][column];
    _state = b->next_state;
    b->action(c);
}

void StateMachine::nop(int const c) {}

void StateMachine::print(int const c) { putchar(c); }

struct Branch const StateMachine::_transitions[3][3] = {
    //   newline         whitespace         other             Inputs/States
    {{BEFORE, &nop}, {BEFORE, &nop}, {INSIDE, &print}},   // before
    {{BEFORE, &print}, {AFTER, &nop}, {INSIDE, &print}},  // inside
    {{BEFORE, &print}, {AFTER, &nop}, {AFTER, &nop}}      // after
};

void log(char msg[]) { std::cout << msg << std::endl; }

int main() {
    int c;
    StateMachine m;

    std::cout << "TEST" << std::endl;

    while ((c = getchar()) != EOF) {
        m.feedChar(c);
    }

    log("TEST2");

    return 0;
}