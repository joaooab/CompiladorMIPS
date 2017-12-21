.data
a: .word 0
b: .word 0
.text
.globl main
main:
li $t3, 0
subu $sp, $sp, 4
sw $t3, ($sp)
lw $t0, ($sp)
addu $sp, $sp, 4
sw $t0, a
li $t3, 0
subu $sp, $sp, 4
sw $t3, ($sp)
lw $t0, ($sp)
addu $sp, $sp, 4
sw $t0, b
li $t3, 2
subu $sp, $sp, 4
sw $t3, ($sp)
lw $t0, ($sp)
addu $sp, $sp, 4
sw $t0, a
