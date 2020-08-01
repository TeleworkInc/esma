#include <ctype.h>
#include <stdio.h>


enum State {BEFORE, INSIDE, AFTER};


void step(enum State* const s, int const c) {
  switch (*s) {
    case BEFORE:
      if (!isspace(c)) {
        putchar(c);
        *s = INSIDE;
      }
      
      break;
    case INSIDE:
      if (c == '\n') {
        putchar(c);
        *s = BEFORE;
      } else if (isspace(c)) {
        *s = AFTER;
      } else {
        putchar(c);
      }
        
      break;
    case AFTER:
      if (c == '\n') {
        putchar(c);
        *s = BEFORE;
      }
      
      break;
  }
}


int main(void) {
  int c;
  enum State s = BEFORE;

  while ((c = getchar()) != EOF) {
    step(&s, c);
  }

  return 0;
}