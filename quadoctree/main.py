import math

import pygame
from pygame.locals import *


size = [320, 320]  # 640, 320
block_size = 10


def new_array(lenght, def_value=None):
    a = []
    for i in range(0, int(lenght)):
        a.append(def_value)
    return a


class Grid(object):

    def __init__(self, background, screen):
        self.background = background
        self.screen = screen
        grid = new_array(size[0] / block_size)
        for i in range(0, len(grid)):
            grid[i] = new_array(size[1] / block_size)
        self.grid = grid

    def draw(self):
        for x in range(1, size[0] + 1):
            for y in range(1, size[1]):
                if x % block_size == 0 or y % block_size == 0:
                    self.background.set_at((x, y), (25, 35, 25))
        x = -1
        for yrow in self.grid:
            x, y = x + 1, -1
            for val in yrow:
                y = y + 1
                if val == 1:
                    self.draw_grid_block(x, y)

    def draw_grid_block(self, x, y, rgb=(130, 130, 130), draw_size=None, thicknes=0):
        if not draw_size:
            draw_size = [block_size, block_size]
        pygame.draw.rect(self.screen, rgb,
                         (x * block_size + 1, y * block_size + 1,
                          draw_size[0] - 1, draw_size[1] - 1),
                         (thicknes))

    def draw_block_by_coord(self, xy, rgb=(130, 130, 130), size=None, thicknes=0):
        self.draw_grid_block(int(xy[0] / block_size), int(xy[1] / block_size),
                             rgb, size, thicknes)

    def draw_border(self, x, y, h, rgb=(60, 130, 130), thicknes=1):
        pygame.draw.rect(self.screen, rgb,
                         (x * block_size, y * block_size,
                          h * block_size + 1, h * block_size + 1),
                         (thicknes))

    def draw_target(self, xy):
        pygame.draw.rect(
            self.screen, (225, 15, 55), (xy[0] - 2, xy[1] - 2, 5, 5), (0))

    def add_block(self, xy, cursor_block_size):
        grid_x = int(xy[0] / block_size)
        grid_y = int(xy[1] / block_size)
        for ix in range(1, cursor_block_size + 1):
            _grid_x = grid_x + (ix - 1)
            for iy in range(1, cursor_block_size + 1):
                _grid_y = grid_y + (iy - 1)
                if _grid_x < len(self.grid) and _grid_y < len(self.grid):
                    self.grid[_grid_x][_grid_y] = 1

    def clear_grid(self):
        grid = new_array(size[0] / block_size)
        for i in range(0, len(grid)):
            grid[i] = new_array(size[1] / block_size)
        self.grid = grid


class QTree(object):

    def __init__(self, grid):
        self.grid = grid
        self.root = []
        self.display_nodes = []
        self.tree_level = 0
        self.search_path = [0]
        self.res = None

    def map_tree(self):
        self.display_nodes = []
        self.tree_level = 0
        self.res = None
        self.root = self._split_blocks_(0, 1, 0, 0)
        if type(self.root) == int:
            self.root = [self.root]

    def _split_blocks_(self, level, block_num, x, y):
        if level > self.tree_level:
            self.tree_level = level
        h2 = len(self.grid.grid) / 2 ** level
        split = False
        all_forbidden = True
        for _x in range(len(self.grid.grid)):
            if _x >= x and _x < x + h2:
                for _y in range(len(self.grid.grid[_x])):
                    if _y >= y and _y < y + h2:
                        if self.grid.grid[_x][_y] == 1:
                            split = True
                        else:
                            all_forbidden = False
                    if split and not all_forbidden:
                        break
            if split and not all_forbidden:
                break
        if all_forbidden:
            return 0
        else:
            if not split:
                self.display_nodes.append([x, y, h2])
                return -1
            else:
                return [
                    self._split_blocks_(level + 1, 1, x, y),
                    self._split_blocks_(level + 1, 2, x + h2 / 2, y),
                    self._split_blocks_(level + 1, 3, x, y + h2 / 2),
                    self._split_blocks_(level + 1, 4, x + h2 / 2, y + h2 / 2)]

    def render_tree(self):
        for node in self.display_nodes:
            self.grid.draw_border(node[0], node[1], node[2])

    def find_node(self, xy):
        xy = [int(xy[0] / block_size), int(xy[1] / block_size)]
        if xy[0] < len(self.grid.grid) and xy[1] < len(self.grid.grid):
            self.search_path = [0]
            if self.tree_level > 0:
                self.res = self._explore_node_(1, xy[0], xy[1], self.root)
            else:
                self.res = self.root[0]

    def _explore_node_(self, level, x, y, node):
        half_xy = len(self.grid.grid) / 2 ** level
        if x < half_xy:
            if y < half_xy:
                self.search_path.append(1)
                if type(node[0]) == int:
                    return node[0]
                else:
                    return self._explore_node_(level + 1, x, y, node[0])
            else:
                self.search_path.append(3)
                if type(node[2]) == int:
                    return node[2]
                else:
                    return self._explore_node_(level + 1, x, y - half_xy, node[2])
        else:
            if y < half_xy:
                self.search_path.append(2)
                if type(node[1]) == int:
                    return node[1]
                else:
                    return self._explore_node_(level + 1, x - half_xy, y, node[1])
            else:
                self.search_path.append(4)
                if type(node[3]) == int:
                    return node[3]
                else:
                    return self._explore_node_(level + 1, x - half_xy, y - half_xy, node[3])


def main():
    pygame.init()
    clock = pygame.time.Clock()
    screen = pygame.display.set_mode((size[0] + 1 + 210, size[1] + 1))

    background = pygame.Surface(screen.get_size()).convert()
    background.fill((0, 0, 0))

    pygame.display.set_caption('Q-Tree')
    grid = Grid(background, screen)
    qtree = QTree(grid)
    qtree.map_tree()
    target = [None, None]

    cursor_block_size = 1

    def draw_text(msg, pos, temp=False):
        if pygame.font:
            font = pygame.font.Font(None, 14)
            text = font.render(msg, 1, (200, 200, 10))
            if temp:
                screen.blit(text, pos)
            else:
                background.blit(text, pos)

    draw_text("Input:", (size[0] + 70, 5))
    draw_text("L_MOUSE:                   place a block", (size[0] + 15, 25))
    draw_text("BACK_SPACE:                  clear grid", (size[0] + 15, 40))
    draw_text("RIGHT_SHIFT:           create a Q-Tree", (size[0] + 15, 55))
    draw_text("1,2,3,4,5:         block size = 1,2,4,8,16", (size[0] + 15, 70))

    # Main loop
    while 1:
        clock.tick(60)
        mouse_pos = pygame.mouse.get_pos()
        screen.blit(background, (0, 0))
        background.set_at((10, 10), (255, 255, 255))

        # Input
        for event in pygame.event.get():
            if event.type == QUIT:
                return
            if event.type == MOUSEBUTTONDOWN:
                if event.button == 1:
                    grid.add_block(mouse_pos, cursor_block_size)
                if event.button == 3:
                    qtree.find_node(mouse_pos)
                    target = mouse_pos
            if event.type == KEYDOWN:
                if event.key == K_BACKSPACE:
                    grid.clear_grid()
                    qtree.map_tree()
                    target = [None, None]
                if event.key == K_RSHIFT:
                    qtree.map_tree()
                if event.key == K_1:
                    cursor_block_size = 1
                if event.key == K_2:
                    cursor_block_size = 2
                if event.key == K_3:
                    cursor_block_size = 4
                if event.key == K_4:
                    cursor_block_size = 8
                if event.key == K_5:
                    cursor_block_size = 16

        # Rendering
        grid.draw()
        qtree.render_tree()
        if target != [None, None]:
            grid.draw_target(target)
            draw_text("Point coordinate: {}".format(target),
                      (size[0] + 15, 145), True)
            block_status = "EMPTY"
            if qtree.res == 0:
                block_status = "OCCUPIED"
            draw_text("Selected block is " + block_status,
                      (size[0] + 15, 160), True)
            draw_text("Tree path to point:", (size[0] + 15, 175), True)
            base_x, base_y = size[0] + 95, 195
            base_dif = int(30 / len(qtree.search_path))
            for val in qtree.search_path:
                if val == 0:
                    draw_text("[ROOT LEVEL]", (base_x - 11, base_y), True)
                else:
                    if val == 1:
                        base_x = base_x - base_dif * 3
                    elif val == 2:
                        base_x = base_x - base_dif
                    elif val == 3:
                        base_x = base_x + base_dif
                    elif val == 4:
                        base_x = base_x + base_dif * 3
                    if base_dif > 1:
                        base_dif = base_dif - 1
                    draw_text("[{}]".format(val), (base_x, base_y), True)
                base_y = base_y + 15
        draw_text("Selected block size: {}".format(cursor_block_size),
                  (size[0] + 15, 105), True)
        draw_text("Tree depth level: {}".format(qtree.tree_level),
                  (size[0] + 15, 115), True)
        pygame.display.flip()

if __name__ == '__main__':
    main()
