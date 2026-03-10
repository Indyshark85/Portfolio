#!/usr/bin/env python3
import json
import subprocess
import shlex
import itertools # New library import for the sdiff function

# ANSI color codes for pretty printing
class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

# NEW FUNCTION: Generates a side-by-side diff string
def generate_sdiff(expected_str, actual_str):
    """Creates a formatted, side-by-side comparison of two strings."""
    expected_lines = expected_str.splitlines()
    actual_lines = actual_str.splitlines()
    
    diff_lines = []
    # Use a fixed width for the left column for clean alignment
    width = max(len(line) for line in expected_lines) if expected_lines else 20
    
    for expected, actual in itertools.zip_longest(expected_lines, actual_lines, fillvalue=""):
        marker = bcolors.OKGREEN + "." + bcolors.ENDC # Default is match
        if expected != actual:
            if expected == "":
                marker = bcolors.OKGREEN + ">" + bcolors.ENDC # Extra line in actual
            elif actual == "":
                marker = bcolors.FAIL + "<" + bcolors.ENDC # Missing line in actual
            else:
                marker = bcolors.WARNING + "|" + bcolors.ENDC # Lines differ
        
        diff_lines.append(f"  {expected:<{width}} {marker} {actual}")
        
    return "\n".join(diff_lines)


def compile_programs():
    """Compiles all the C programs required for the tests."""
    print(f"{bcolors.HEADER}--- Compiling C files ---{bcolors.ENDC}")
    programs_to_compile = {
        "p1_phone": "gcc -o p1_phone p1_phone.c",
        "p2_triangle": "gcc -o p2_triangle p2_triangle.c",
        "p3_calculator": "gcc -o p3_calculator p3_calculator.c -lm",
        "p4_income": "gcc -o p4_income p4_income.c",
        "p5_domain": "gcc -o p5_domain p5_domain.c",
        "p6_prime": "gcc -o p6_prime p6_prime.c -lm",
        "p7_fizzbuzz": "gcc -o p7_fizzbuzz p7_fizzbuzz.c",
        "p8_binary": "gcc -o p8_binary p8_binary.c",
        "p9_predator": "gcc -o p9_predator p9_predator.c",
        "p10_encryption": "gcc -o p10_encryption p10_encryption.c",
    }
    
    all_compiled = True
    for name, command in programs_to_compile.items():
        try:
            subprocess.run(shlex.split(command), check=True, capture_output=True)
            print(f"  {bcolors.OKBLUE}Successfully compiled {name}{bcolors.ENDC}")
        except subprocess.CalledProcessError as e:
            print(f"  {bcolors.FAIL}Failed to compile {name}{bcolors.ENDC}")
            print(e.stderr.decode())
            all_compiled = False
            
    if not all_compiled:
        print(f"\n{bcolors.FAIL}{bcolors.BOLD}Compilation failed. Aborting tests.{bcolors.ENDC}")
        exit(1)
    print(f"{bcolors.HEADER}--- Compilation Complete ---\n{bcolors.ENDC}")


def run_tests():
    """Runs the autograder tests defined in tests.json."""
    try:
        with open('tests.json', 'r') as f:
            tests = json.load(f)
    except FileNotFoundError:
        print(f"{bcolors.FAIL}Error: tests.json not found.{bcolors.ENDC}")
        return

    total_passed = 0
    
    for i, test in enumerate(tests):
        test_name = test.get("test_name", f"Test {i+1}")
        program = test.get("program")
        test_input = test.get("input", "")
        expected_output = test.get("expected_output", "").strip()

        print(f"{bcolors.BOLD}Running: {test_name}...{bcolors.ENDC}", end=' ')

        try:
            result = subprocess.run(
                [program],
                input=test_input,
                capture_output=True,
                text=True,
                timeout=5
            )
            
            actual_output_full = result.stdout.strip()
            
            if program in ["./p5_domain", "./p9_predator"]:
                 last_line_actual = actual_output_full.splitlines()[-1] if actual_output_full else ""
                 passed = (last_line_actual.strip() == expected_output)
                 actual_output_to_display = actual_output_full # Show full output on fail
            else:
                 passed = (actual_output_full == expected_output)
                 actual_output_to_display = actual_output_full

            if passed:
                print(f"{bcolors.OKGREEN}PASS{bcolors.ENDC}")
                total_passed += 1
            else:
                # UPDATED FAILURE REPORTING
                print(f"{bcolors.FAIL}FAIL{bcolors.ENDC}")
                print(f"  {bcolors.BOLD}Side-by-Side Difference (. match, | differ, < missing, > extra):{bcolors.ENDC}")
                diff_report = generate_sdiff(expected_output, actual_output_to_display)
                print(diff_report)


        except FileNotFoundError:
            print(f"{bcolors.FAIL}FAIL (Error: Program '{program}' not found. Was it compiled?){bcolors.ENDC}")
        except subprocess.TimeoutExpired:
            print(f"{bcolors.FAIL}FAIL (Timed Out){bcolors.ENDC}")
        except Exception as e:
            print(f"{bcolors.FAIL}FAIL (An unexpected error occurred: {e}){bcolors.ENDC}")

    print(f"\n{bcolors.HEADER}--- Test Summary ---{bcolors.ENDC}")
    print(f"{bcolors.BOLD}Total Tests Passed: {total_passed} / {len(tests)}{bcolors.ENDC}")
    print(f"{bcolors.HEADER}--------------------{bcolors.ENDC}")

if __name__ == "__main__":
    compile_programs()
    run_tests()