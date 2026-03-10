#!/usr/bin/env python3
import json
import subprocess
import shlex
import os
import re 

class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

def generate_interactive_report(test_case, raw_actual_output):
    """
    Creates a user-friendly report for a failed test.
    """
    final_output = test_case.get("final_output", "")

    print(f"\n  {bcolors.BOLD}Expected Final Output:{bcolors.ENDC}")
    print(f"  ---")
    print(final_output)
    print(f"  ---")

    print(f"\n  {bcolors.BOLD}Actual Raw Output (received from program):{bcolors.ENDC}")
    print(f"  ---")
    print(raw_actual_output)
    print(f"  ---")
    print("")


def compile_programs(programs_to_compile):
    """
    Compiles all C files defined in the config.
    """
    print(f"{bcolors.HEADER}--- Compiling C files ---{bcolors.ENDC}")

    successfully_compiled = set()
    for name, command in programs_to_compile.items():
        source_file = command.split()[-1]
        if not os.path.exists(source_file):
            print(f"  {bcolors.WARNING}Skipping compilation for {name}: Source file '{source_file}' not found.{bcolors.ENDC}")
            continue

        try:
            proc = subprocess.run(shlex.split(command), check=True, capture_output=True, text=True)
            print(f"  {bcolors.OKBLUE}Successfully compiled {name}{bcolors.ENDC}")
            successfully_compiled.add(name)
        except subprocess.CalledProcessError as e:
            print(f"  {bcolors.FAIL}Failed to compile {name}:{bcolors.ENDC}")
            print(f"  {bcolors.WARNING}{e.stderr.strip()}{bcolors.ENDC}")

    print(f"{bcolors.HEADER}--- Compilation Complete ---\n{bcolors.ENDC}")
    return successfully_compiled


def run_tests(compiled_programs, test_file):
    """
    Runs the autograder tests defined in the specified JSON file.
    """
    try:
        with open(test_file, 'r') as f:
            tests = json.load(f)
    except FileNotFoundError:
        print(f"{bcolors.FAIL}Error: Test file '{test_file}' not found.{bcolors.ENDC}")
        return
    except json.JSONDecodeError as e:
        print(f"{bcolors.FAIL}Error: Test file '{test_file}' is not valid JSON: {e}{bcolors.ENDC}")
        return

    total_passed = 0
    total_run = 0

    for test in tests:
        test_name = test.get("test_name", "Unknown Test")
        program_name = test.get("program", "").replace("./", "")

        print(f"{bcolors.BOLD}Running: {test_name}...{bcolors.ENDC}", end='')

        if program_name not in compiled_programs:
            print(f" {bcolors.WARNING}SKIPPED (Compilation Failed){bcolors.ENDC}")
            continue

        total_run += 1

        input_lines = test.get("input_lines", [])
        test_input = "\n".join(input_lines) + "\n"
        final_output = test.get("final_output", "")

        try:
            result = subprocess.run(
                [f"./{program_name}"],
                input=test_input,
                capture_output=True,
                text=True,
                timeout=5,
                encoding='utf-8'
            )
            
            raw_output = result.stdout
            
            # This uses a regular expression to find and remove the ">> " prompt
            # from the beginning of any line in the output. This is the most
            # reliable way to clean the output for comparison.
            # re.MULTILINE makes '^' match the start of each line.
            actual_output_processed = re.sub(r'^\s*>>\s?', '', raw_output, flags=re.MULTILINE)
            
            # Final comparison uses strip() on both sides to handle any
            # leading/trailing whitespace differences, ensuring a fair comparison.
            passed = (actual_output_processed.strip() == final_output.strip())

            if passed:
                print(f" {bcolors.OKGREEN}PASS{bcolors.ENDC}")
                total_passed += 1
            else:
                print(f" {bcolors.FAIL}FAIL{bcolors.ENDC}")
                generate_interactive_report(test, raw_output)

        except Exception as e:
            print(f" {bcolors.FAIL}FAIL (An error occurred: {e}){bcolors.ENDC}")

    print(f"\n{bcolors.HEADER}--- Test Summary ---{bcolors.ENDC}")
    print(f"{bcolors.BOLD}Total Tests Passed: {total_passed} / {total_run} (out of {len(tests)} total tests){bcolors.ENDC}")
    print(f"{bcolors.HEADER}--------------------{bcolors.ENDC}")

if __name__ == "__main__":
    assignment_config = {
        "c_files_to_compile": {
            "str_manip": "gcc -o str_manip -Wall str_manip.c"
        },
        "test_file": "tests.json"
    }

    compiled = compile_programs(assignment_config["c_files_to_compile"])
    if "str_manip" in compiled:
        run_tests(compiled, assignment_config["test_file"])

